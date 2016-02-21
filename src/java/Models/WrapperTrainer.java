/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

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
    private final char[] CHARS_TO_CHECK = {'[', '(', '{', '<',  '\"', '\'', '>', '}', ')', ']'}; //if change need to change methods that use
    
    List<String> htmlDocs = new ArrayList<>();
    
    WrapperTester wrapperTester = new WrapperTester();
    
    private List<SiteFeatures> trainingData;
    
    public WrapperTrainer(Connection conn) {
        wrapperDB = new WrapperDB(conn);
    }
    
    public void trainSystem(List<SiteFeatures> trainingData) {
        this.trainingData = trainingData;
        List<Wrapper> wrapperList = new ArrayList<Wrapper>();
        htmlDocs = new ArrayList<>();
        
        for (int i = 0; i < trainingData.size(); i++) {
            //wrapperList.add(generateWrapper(siteList.get(i)));
            wrapperList.add(generateFromText(trainingData.get(i)));
        }
        Wrapper wrapper = aggregateWrappers(wrapperList);
        
        wrapperDB.addWrapper(wrapper);
        //save into db at end
    }
    
    private Wrapper generateFromText(SiteFeatures feature) {
        Wrapper wrapper = null;
        html = null;
        try {
            Document doc = Jsoup.connect(feature.getUrl()).get();
            html = doc.body().toString();
            htmlDocs.add(html);
            int length = html.length();
            wrapper = createWrapperFromSearch(searchFileForFeatures(feature), feature.getDomain());
            System.out.println("");
        } catch (IOException ex) {
            Logger.getLogger(WrapperHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return wrapper;
    }

    private List<WrapperSearchResult> searchFileForFeatures(SiteFeatures siteFeature) {
        List<WrapperSearchResult> searchList = new ArrayList<WrapperSearchResult>();

        for (FeatureEnum feature : FeatureEnum.values()) {
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

    private Wrapper createWrapperFromSearch(List<WrapperSearchResult> searchList, String domain) {
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
                    if(!added){
                        //DO THIS
                        System.out.println("");
                    }
                }
            }
        }

        return new Wrapper(domain, head, tail, aggregatedRuleList);
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

        if(left == "" || right == "") return null;
        
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
                if(aggregated.length() == 0)return "";
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
            String expectedValue = trainingData.get(i).getFeatureMap().get(rule.getFeatureName());

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
