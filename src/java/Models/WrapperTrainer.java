/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Enums.FeatureEnum;
import SQL.WrapperDB;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Kyran
 */
public class WrapperTrainer {

    WrapperDB wrapperDB;

    String html;
    private final int NUM_OF_CHARS = 150;
    private final int HEAD_TAIL_CHARS = 600;
    private final char[] CHARS_TO_CHECK = {'[', '(', '{', '<', '\"', '\'', '>', '}', ')', ']'}; //if change need to change methods that use

    List<String> htmlDocs = new ArrayList<>();

    WrapperTester wrapperTester = new WrapperTester();

    private List<SiteFeatures> indTrainingData;
    private List<TicketListFeatures> listTrainingData;

    public WrapperTrainer(Connection conn) {
        wrapperDB = new WrapperDB(conn);
    }

    public void trainIndividual(List<SiteFeatures> trainingData) {
        this.indTrainingData = trainingData;
        List<Wrapper> wrapperList = new ArrayList<Wrapper>();
        htmlDocs = new ArrayList<>();

        for (int i = 0; i < trainingData.size(); i++) {
            //wrapperList.add(generateWrapper(siteList.get(i)));
            wrapperList.add(generateIndWrapper(trainingData.get(i)));
        }
        Wrapper wrapper = aggregateWrappers(wrapperList);
        wrapper.setType(0);
        wrapperDB.addWrapper(wrapper);
        //save into db at end
    }

    public void trainLists(List<TicketListFeatures> trainingData) {
        this.listTrainingData = trainingData;

        List<Wrapper> wrapperList = new ArrayList<Wrapper>();
        htmlDocs = new ArrayList<>();

        for (int i = 0; i < trainingData.size(); i++) {
            //wrapperList.add(generateWrapper(siteList.get(i)));
            wrapperList.add(generateListWrapper(trainingData.get(i)));
        }
        Wrapper wrapper = aggregateWrappers(wrapperList);
        wrapper.setType(1);
        wrapperDB.addWrapper(wrapper);
    }

    private Wrapper generateIndWrapper(SiteFeatures feature) {
        Wrapper wrapper = null;
        html = null;
        try {
            Document doc = Jsoup.connect(feature.getUrl()).get();
            html = doc.body().toString();
            htmlDocs.add(html);
            wrapper = createIndWrapperFromSearch(searchFileForIndFeatures(feature), feature.getDomain());
        } catch (IOException ex) {
            Logger.getLogger(WrapperHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return wrapper;
    }

    private Wrapper generateListWrapper(TicketListFeatures feature) {
        Wrapper wrapper = null;
        html = null;
        try {
            Document doc = Jsoup.connect(feature.getUrl()).get();
            html = doc.body().toString();
            htmlDocs.add(html);
            wrapper = createListWrapperFromSearch(searchFileForListFeatures(feature), feature.getDomain());
        } catch (IOException ex) {
            Logger.getLogger(WrapperHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return wrapper;
    }

    private List<WrapperSearchResult> searchFileForIndFeatures(SiteFeatures siteFeature) {
        List<WrapperSearchResult> searchList = new ArrayList<WrapperSearchResult>();

        for (FeatureEnum feature : FeatureEnum.values()) {
            if (feature == FeatureEnum.URL) {
                continue;
            }

            String value = siteFeature.getFeatureMap().get(feature);
            if (value.equals("")) {
                continue;
            }
            int index = 0;
            while (true) {
                index = html.indexOf(value, index);
                if (index == -1) {
                    break;
                } else {
                    searchList.add(new WrapperSearchResult(index, value, feature));
                }
                index += value.length();
            }
            System.out.println("");
        }
        return searchList;
    }

    private ArrayList<ArrayList<WrapperSearchResult>> searchFileForListFeatures(TicketListFeatures siteFeature) {
        ArrayList<ArrayList<WrapperSearchResult>> searchList = new ArrayList<ArrayList<WrapperSearchResult>>();
        //storing as a list of lists to keep track of how many times each url was found and keep seperated

        ArrayList<WrapperSearchResult> tempList;
        for (int i = 0; i < siteFeature.getUrlList().size(); i++) {
            tempList = new ArrayList<>();
            String value = siteFeature.getUrlList().get(i);
            if (value.equals("")) {
                continue;
            }
            int index = 0;
            while (true) {

                index = html.indexOf(value, index);
                if (index == -1) {
                    break;
                } else {
                    tempList.add(new WrapperSearchResult(index, value, FeatureEnum.URL));
                }
                index += value.length();
            }
            searchList.add(tempList);
        }
        return searchList;
    }

    private Wrapper createIndWrapperFromSearch(List<WrapperSearchResult> searchList, String domain) {
        Wrapper wrapper = new Wrapper(domain);
        int min = -1;
        int max = -1;

        for (int i = 0; i < searchList.size(); i++) {
            WrapperSearchResult searchResult = searchList.get(i);
            Rule rule = new Rule(searchResult.getFeature());
            rule.setLeft(searchResult, searchList, html);
            rule.setRight(searchResult, searchList, html);
            //set open and close here if is valid
            wrapper.addRule(rule);

            //aggregate the rules here
            //set min and max to be used for getting head and tail
            if (min == -1 || min > searchResult.getStartIndex() - 1) {
                min = searchResult.getStartIndex() - 1; //-1 so that it is set before the beginning of the feature
            }
            if (max == -1 || max < searchResult.getStartIndex() + searchResult.getValue().length()) {
                max = searchResult.getStartIndex() + searchResult.getValue().length();
            }
        }

        //use min and max to set head and tail here
        wrapper.setHead(html.substring(min - HEAD_TAIL_CHARS > 0 ? min - HEAD_TAIL_CHARS : 0, min)); //need extra validation to check if is above 0?
        wrapper.setTail(html.substring(max, max + HEAD_TAIL_CHARS > html.length() ? html.length() : max + HEAD_TAIL_CHARS));//need extra validation to check if doesnt exceed length of string 0?
        //use 1000 as needs much more wiggle room to try and find common ground with other sites

        return wrapper;
    }

    private Wrapper createListWrapperFromSearch(ArrayList<ArrayList<WrapperSearchResult>> searchList, String domain) {
        Wrapper wrapper = new Wrapper(domain);
        int min = -1;
        int max = -1;

        int minSize = -1;

        for (int i = 0; i < searchList.size(); i++) {
            int size = searchList.get(i).size();
            if (minSize == -1 || minSize > size) {
                minSize = size;
            }
        }
        List<Rule> ruleList = new ArrayList<>();
        //first need to agregate within itself and then check whether matches the other rules
        for (int i = 0; i < minSize; i++) {
            List<Rule> tempRuleList = new ArrayList<>();
            for (int j = 0; j < searchList.size(); j++) {
                WrapperSearchResult searchResult = searchList.get(j).get(i);
                Rule rule = new Rule(searchResult.getFeature());
                rule.setLeft(searchResult, null, html);
                rule.setRight(searchResult, null, html);

                if (min == -1 || min > searchResult.getStartIndex() - 1) {
                    min = searchResult.getStartIndex() - 1; //-1 so that it is set before the beginning of the feature
                }
                if (max == -1 || max < searchResult.getStartIndex() + searchResult.getValue().length()) {
                    max = searchResult.getStartIndex() + searchResult.getValue().length();
                }
                tempRuleList.add(rule);
            }
            Rule rule = aggregateRuleList(tempRuleList);
            rule.setOpen(html.substring(min - HEAD_TAIL_CHARS > 0 ? min - HEAD_TAIL_CHARS : 0, min));
            rule.setClose(html.substring(max, max + HEAD_TAIL_CHARS > html.length() ? html.length() : max + HEAD_TAIL_CHARS));
            
            
            ruleList.add(rule);
        }

//        for (int i = 0; i < searchList.size(); i++) {
//            WrapperSearchResult searchResult = searchList.get(i);
//            Rule rule = new Rule(searchResult.getFeature());
//            rule.setLeft(searchResult, searchList, html);
//            rule.setRight(searchResult, searchList, html);
//            //set open and close here if is valid
//            wrapper.addRule(rule);
//
//            //set min and max to be used for getting head and tail
//            if (min == -1 || min > searchResult.getStartIndex() - 1) {
//                min = searchResult.getStartIndex() - 1; //-1 so that it is set before the beginning of the feature
//            }
//            if (max == -1 || max < searchResult.getStartIndex() + searchResult.getValue().length()) {
//                max = searchResult.getStartIndex() + searchResult.getValue().length();
//            }
//        }
        wrapper.setHead("<body>");
        wrapper.setTail("</body>");
        wrapper.setRuleList(ruleList);
        
        return wrapper;
    }

    // <editor-fold desc="Wrapper aggregation">
    private Wrapper aggregateWrappers(List<Wrapper> wrapperList) {
        //after have aggregated all those that can, will 

        List<String> tempHeadList = new ArrayList<String>();
        List<String> tempTailList = new ArrayList<String>();
        List<Rule> tempRuleList;

        String head = null;
        String tail = null;
        String domain = wrapperList.get(0).getDomain();
        List<Rule> aggregatedRuleList = new ArrayList<Rule>();

        //set up temp lists
        for (int i = 0; i < wrapperList.size(); i++) {
            tempHeadList.add(wrapperList.get(i).getHead());
            tempTailList.add(wrapperList.get(i).getTail());
        }
        //aggregate head and tail
        head = compareLR(tempHeadList, true, HEAD_TAIL_CHARS);
        tail = compareLR(tempTailList, false, HEAD_TAIL_CHARS);

        //group into lists of each rule type
        //handles events where creates different number of rules for a feature
        ArrayList<ArrayList<Rule>> listOfRuleLists;
        for (FeatureEnum feature : FeatureEnum.values()) {
//            if (feature == FeatureEnum.URL) {
//                continue;
//            }

            listOfRuleLists = new ArrayList<ArrayList<Rule>>();
            for (int i = 0; i < wrapperList.size(); i++) {
                listOfRuleLists.add(wrapperList.get(i).filterRules(feature));
            }
            if (listOfRuleLists.size() > 0) {
                if (haveSameNumOfRules(listOfRuleLists)) {
                    for (int i = 0; i < listOfRuleLists.get(0).size(); i++) {
                        tempRuleList = new ArrayList<>();
                        for (int j = 0; j < listOfRuleLists.size(); j++) {
                            Rule rule = listOfRuleLists.get(j).get(i);
                            tempRuleList.add(rule);
                        }
                        Rule aggregatedRule = aggregateRuleList(tempRuleList);
                        if (aggregatedRule != null) {
                            if (testRule(aggregatedRule)) {
                                aggregatedRuleList.add(aggregatedRule);
                            }
                        }
                    }
                } else {
                    //if different sizes

                    //just temporary code for now
                    boolean added = false;

                    for (int i = 0; i < minimumNumOfRules(listOfRuleLists); i++) {
                        tempRuleList = new ArrayList<>();
                        for (int j = 0; j < listOfRuleLists.size(); j++) {
                            Rule rule = listOfRuleLists.get(j).get(i);
                            tempRuleList.add(rule);
                        }
                        Rule aggregatedRule = aggregateRuleList(tempRuleList);
                        if (aggregatedRule != null) {
                            if (testRule(aggregatedRule)) {
                                aggregatedRuleList.add(aggregatedRule);
                                added = true;
                            }
                        }
                    }
                    //if not been ab le to add one then have to go through each possible comination of rules until finds a matching set that pass test
                    //only want to do this as a last resort whcih is why its left until now
                    if (!added) {
                        //DO THIS
                        System.out.println("");
                    }
                }
            }
        }

        return new Wrapper(domain, head, tail, aggregatedRuleList, wrapperList.get(0).getType());
    }

    private int minimumNumOfRules(List<Wrapper> wrapperList) {
        int min = wrapperList.get(0).getRuleList().size();

        for (int i = 1; i < wrapperList.size(); i++) {
            if (wrapperList.get(i).getRuleList().size() < min) {
                min = wrapperList.get(i).getRuleList().size();
            }
        }

        return min;
    }

    private int minimumNumOfRules(ArrayList<ArrayList<Rule>> listOfRuleLists) {
        int min = listOfRuleLists.get(0).size();

        for (int i = 1; i < listOfRuleLists.size(); i++) {
            if (min > listOfRuleLists.get(i).size()) {
                min = listOfRuleLists.get(i).size();
            }
        }

        return min;
    }

    private boolean haveSameNumOfRules(ArrayList<ArrayList<Rule>> listOfRuleLists) {
        int count = listOfRuleLists.get(0).size();

        for (int i = 1; i < listOfRuleLists.size(); i++) {
            if (count != listOfRuleLists.get(i).size()) {
                return false;
            }
        }

        return true;
    }

    private Rule aggregateRuleList(List<Rule> ruleList) {
        // can have multiple potential rules for same feature in same domain going in here
        //this will try and get the rule that matches all and tests, if doesnt work could potentially move to next set of rules for that feature 
        //(this is where web page contains same bit of info in different places)
        //return null if cant create a rule for it
        List<String> leftList = new ArrayList<>();
        List<String> rightList = new ArrayList<>();
        //List<String> openList = new ArrayList<>();
        //List<String> closeList = new ArrayList<>();

        for (int i = 0; i < ruleList.size(); i++) {
            leftList.add(ruleList.get(i).getLeft());
            rightList.add(ruleList.get(i).getRight());
            //openList.add(ruleList.get(i).getOpen());
            //closeList.add(ruleList.get(i).getClose());
        }
        FeatureEnum feature = ruleList.get(0).getFeatureName();
        String left = compareLR(leftList, true, NUM_OF_CHARS);
        String right = compareLR(rightList, false, NUM_OF_CHARS);
        
        String open = "";
        String close = "";

        if (left == "" || right == "") {
            return null;
        }

        return new Rule(feature, open, close, left, right);
    }
    // </editor-fold>
    // <editor-fold desc="String comparison">

    public String compareLR(List<String> pStringList, boolean isReverse, int numOfChars) {
        //this compares strings that will be in the used for the left or right
        //if is going to be for the left, need to reverse the string and then iterate normally
        //because want to get the closest possible
        List<String> stringList = pStringList;
        String aggregated = "";

        if (isReverse) {
            for (int i = 0; i < stringList.size(); i++) {
                stringList.set(i, new StringBuilder(stringList.get(i)).reverse().toString());
            }
        }
        outerloop:
        for (int i = 0; i < numOfChars; i++) {
            //once add something into the aggregated string - remove from items in the string list so dont have to keep up with indexes
            char c1;
            if (compareFirstChar(stringList)) {
                aggregated += stringList.get(0).charAt(0);
                for (int j = 0; j < stringList.size(); j++) {
                    stringList.set(j, stringList.get(j).substring(1));
                }
            } else {
                //if hasnt matched then have to assume that there is an interchangeable piece of info here
                if (aggregated.length() == 0) {
                    return "";
                }
                else if(aggregated.replace(" ", "").lastIndexOf(WrapperExecutor.MODIFIER_STRING) > (aggregated.replace(" ", "").length() - WrapperExecutor.MODIFIER_STRING.length() - 2)){
                    break outerloop;
                }
                char c = compareToCharArray(aggregated.charAt(aggregated.length() - 1), isReverse);
                if (c != ' ' || (c = compareToCharArray(aggregated, isReverse)) != ' ') {
                    for (int j = 0; j < stringList.size(); j++) {
                        String rule = stringList.get(j);
                        int index = rule.indexOf(c);
                        if (index > -1) {
                            stringList.set(j, rule.substring(index));
                        } else {
                            break outerloop;
                        }
                    }
                    aggregated += WrapperExecutor.MODIFIER_STRING;
                } else {
                    break;
                }
            }
            for (int j = 0; j < stringList.size(); j++) {
                if (stringList.get(j).length() == 0) {
                    break outerloop;
                }
            }
        }
        if (isReverse) {
            aggregated = new StringBuilder(aggregated).reverse().toString();
        }
        return aggregated;
    }

    private char compareToCharArray(char character, boolean isReverse) {
        //looks to see if contains a character that would require a closing character eg <,(
        //return reciprical char
        //checks the immediate character
        int min = isReverse ? 3 : 0; //is 3 because want to check both opening and closing occurances of <>
        int max = isReverse ? 10 : 7;
        for (int i = min; i < max; i++) {
            if (CHARS_TO_CHECK[i] == character) {
                if (i == 4 || i == 5) {
                    //IGNORE QUOTES FOR NOW

                    //return CHARS_TO_CHECK[i];
                } else {
                    return CHARS_TO_CHECK[CHARS_TO_CHECK.length - 1 - i];
                }
            }
        }
        return ' ';
    }

    private char compareToCharArray(String string, boolean isReverse) {
        //looks to see if contains a character that would require a closing character eg <,(
        //return reciprical char
        int min = isReverse ? 3 : 0;
        int max = isReverse ? 10 : 7;
        for (int i = string.length() - 1; i >= 0; i--) {
            char character = string.charAt(i);
            for (int j = min; j < max; j++) {
                if (CHARS_TO_CHECK[j] == character) {
                    if (j == 4 || j == 5) {
                        //IGNORE QUOTES FOR NOW

                        //need to check has odd number of this symbol in the list so far else wont work, make sure is an opening 
                        if (i == 0 || getNumOfOccurances(string, CHARS_TO_CHECK[j]) % 2 == 1) //return CHARS_TO_CHECK[j]; 
                        //atm have doubts as what if the content is in the middle of some quotes, wont know what is inside or outside
                        //might be fine for the other method
                        {
                            //return ' ';
                        }
                    } else {
                        //check hasnt been closed later in the loop
                        boolean closed = false;
                        if (i != string.length()) { //if is last char then no chnace it has been closed
                            for (int k = i + 1; k < string.length(); k++) {
                                if (string.charAt(k) == CHARS_TO_CHECK[CHARS_TO_CHECK.length - 1 - j]) {
                                    closed = true;
                                }
                            }
                        }
                        if (!closed) {
                            return CHARS_TO_CHECK[CHARS_TO_CHECK.length - 1 - j];
                        }
                    }
                }
            }
            if (i == string.length() - 21) {
                break; //if go back too far give up
            }
        }
        return ' ';

    }

    private int getNumOfOccurances(String string, char c) {
        //checks how many times a certain character appears in a string
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    public boolean compareFirstChar(List<String> stringList) {
        //compares first character of each string in list and returns true iff they all have same char
        char character = stringList.get(0).charAt(0);
        for (int i = 1; i < stringList.size(); i++) {
            if (character != stringList.get(i).charAt(0)) {
                return false;
            }
        }
        return true;
    }

    // </editor-fold>
    public boolean testRule(Rule rule) {
        //could change so have a discrepency value where can accept if gets 4/5 right or something?
        for (int i = 0; i < htmlDocs.size(); i++) {
            String expectedValue = indTrainingData.get(i).getFeatureMap().get(rule.getFeatureName());

            if (expectedValue != "") {
                String value = wrapperTester.getValFromRule(htmlDocs.get(i), rule);

                //compare to actual value
                if (!value.equals(expectedValue)) {
                    return false;
                }
            }
        }
        return true;
    }

}
