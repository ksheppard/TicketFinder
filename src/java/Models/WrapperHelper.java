/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Kyran
 */
public class WrapperHelper {

    String html;
    private final int NUM_OF_CHARS = 150;
    private final int HEAD_TAIL_CHARS = 1000;
    private final char[] CHARS_TO_CHECK = {'[', '(', '<', '{', '\"', '\'', '}', '>', ')', ']'}; //if change need to change methods that use

    private final String MODIFIER_STRING = "|#|"; //unique string combinatioin that signifies that ignore the next bit of the string

    private List<SiteFeatures> trainingData;

    public WrapperHelper() {
    }

    // <editor-fold desc="Training">
    public void trainSystem() {
        List<Wrapper> wrapperList = new ArrayList<Wrapper>();
        for (int i = 0; i < trainingData.size(); i++) {
            //wrapperList.add(generateWrapper(siteList.get(i)));
            wrapperList.add(generateFromText(trainingData.get(i)));
        }
        Wrapper wrapper = aggregateWrappers(wrapperList);
        //save into db at end
    }

    public void testMethods() {
        //false looking for open, true = close
        char c1 = compareToCharArray('f', true);
        char c2 = compareToCharArray('f', false);
        char c3 = compareToCharArray('<', true);
        char c4 = compareToCharArray('<', false);
        char c5 = compareToCharArray('>', true);
        char c6 = compareToCharArray('>', false);

        char c11 = compareToCharArray("something>", true);
        char c21 = compareToCharArray("something>", false);
        char c31 = compareToCharArray("something", true);
        char c41 = compareToCharArray("something", false);
        char c51 = compareToCharArray("idksd(sdfsdf", true);
        char c61 = compareToCharArray("idksd(sdfsdf", false);

        String s1 = compareLR(Arrays.asList("<p><b>aaaaaaaaaaa", "<p><b>aaaaaaaaaaa", "<p><b>aaaaaaaaaaa"), true, NUM_OF_CHARS);
        String s2 = compareLR(Arrays.asList("<p><b>aaaaaaaaaaa", "<p><b>aaaaaabaaaa", "<p><b>aaaaaaaaaaa"), true, NUM_OF_CHARS);
        String s3 = compareLR(Arrays.asList("<p><b>aaaaaaaaaaa", "<p><b>aaaaaaaaaaa", "<p><b>aaaaaaaaaaa"), false, NUM_OF_CHARS);
        String s4 = compareLR(Arrays.asList("<p><b>aaaaaaaaaaa", "<p><b>aaaaaabaaaa", "<p><b>aaaaaaaaaaa"), false, NUM_OF_CHARS);
        String s5 = compareLR(Arrays.asList("<p><b><div class=\"site-icon favicon favicon-stackoverflow\" title=\"Stack Overflow\"></div>", "<p><b><div class=\"site-icon favicon favicon-stackoverflow\" title=\"Stack Overflow\"></div>", "<p><b><div class=\"site-icon favicon favicon-stackoverflow\" title=\"Stack Overflow\"></div>"), true, NUM_OF_CHARS);
        String s6 = compareLR(Arrays.asList("<p><b><div class=\"site-icon favicon favicon-stackoverflow\" title=\"Stack Overflow\"></div>", "<p><b><div class=\"site-icon favicon favicon-stackoverflow\" title=\"Stack Overflow\"></div>", "<p><b><div class=\"site-icon favicon favicon-stackoverflow\" title=\"Stack Overflow\"></div>"), false, NUM_OF_CHARS);
        String s7 = compareLR(Arrays.asList("<p><b><div class =\"something unique\"", "<p><b><div class =\"somffdsething unique\"", "<p><b><div class =\"something efweunique\""), true, NUM_OF_CHARS);
        String s8 = compareLR(Arrays.asList("<p><b><div class =\"something unique\"", "<p><b><div class =\"somffdsething unique\"", "<p><b><div class =\"something efweunique\""), false, NUM_OF_CHARS);
        String s9 = compareLR(Arrays.asList("<p><b><div class =(something unique)", "<p><b><div class =(somffdsething unique)", "<p><b><div class =(something efweunique)"), true, NUM_OF_CHARS);
        String s10 = compareLR(Arrays.asList("<p><b><div class =(something unique)", "<p><b><div class =(somffdsething unique)", "<p><b><div class =(something efweunique)"), false, NUM_OF_CHARS);
        String s11 = compareLR(Arrays.asList("<p><b>aaaaaaaaaaa", "<p><b>aaaaaaaaaaa", "<p><b>aaaaaaaaaaa"), true, NUM_OF_CHARS);
        String s12 = compareLR(Arrays.asList("<p><b>aaaaaaaaaaa", "<p><b>aaaaaaaaaaa", "<p><b>aaaaaaaaaaa"), false, NUM_OF_CHARS);
    }

//    private Wrapper generateWrapper(SiteFeatures feature) {
//        Wrapper wrapper = null;
//        List<Rule> ruleList = new ArrayList<Rule>();
//        try {
//            Document doc = Jsoup.connect(feature.getUrl()).get();
//            Elements elements = doc.getAllElements();
//            Iterator it = feature.getFeatureMap().entrySet().iterator();
//            while (it.hasNext()) {
//                Map.Entry pair = (Map.Entry) it.next();
//                FeatureEnum featureEnum = FeatureEnum.valueOf(pair.getKey().toString());
//                String value = pair.getValue().toString();
//                it.remove(); // avoids a ConcurrentModificationException
//                if (pair.getValue().toString() != "") {
//                    Elements searchElements = elements.select(String.format("body *:containsOwn(%s)", value));
//                    if (searchElements.size() > 0) {
//                        searchElements = filterSearchElements(searchElements, value);
//                    }
//
//                    for (int i = 0; i < searchElements.size(); i++) {
//                        ruleList.add(createRule(featureEnum, value, searchElements.get(i)));
//                    }
//                }
//            }
//
//        } catch (IOException ex) {
//            Logger.getLogger(WrapperHelper.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return wrapper;
//    }
    // <editor-fold desc="Generate Wrapper">
    private Wrapper generateFromText(SiteFeatures feature) {
        //make so isnt caps sensitive? aka convert everything to lower
        Wrapper wrapper = null;
        html = null;
        try {
            Document doc = Jsoup.connect(feature.getUrl()).get();
            html = doc.body().toString();
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

        //
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
            rule.setLeft(setLeft(searchResult, searchList, i));
            rule.setRight(setRight(searchResult, searchList, i));
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

    private String setLeft(WrapperSearchResult searchResult, List<WrapperSearchResult> searchList, int index) {
        //index and rest of list is so can stop when reaches other data?
        //could put this in the setter for left and right within the rule class - do later
        String left = html.substring(searchResult.getStartIndex() - 1 - NUM_OF_CHARS, searchResult.getStartIndex() - 1);

        return left;
    }

    private String setRight(WrapperSearchResult searchResult, List<WrapperSearchResult> searchList, int index) {

        String right = html.substring(searchResult.getStartIndex() + searchResult.getValue().length(), searchResult.getStartIndex() + searchResult.getValue().length() + NUM_OF_CHARS);

        return right;
    }

    // </editor-fold>
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
        tail = compareLR(tempTailList, true, HEAD_TAIL_CHARS);

        //aggregate the rules
        int index = 0;

        //do some processing on rules to check that have same amount and same types aka found the same thing
        //at the moment am assuming all of this is correct
        for (int i = 0; i < wrapperList.get(0).getRuleList().size(); i++) {
            tempRuleList = new ArrayList<>();
            for (int j = 0; j < wrapperList.size(); j++) {
                Rule rule = wrapperList.get(i).getRule(index);
                tempRuleList.add(rule);
            }

            //may need checks that all rules are for same rule - are they in the same order?
            Rule aggregatedRule = aggregateRuleList(tempRuleList);
            if (aggregatedRule != null) {
                if(testRule(aggregatedRule)){
                aggregatedRuleList.add(aggregatedRule);
                }
            }

            index++;
            if (index == wrapperList.get(0).getRuleList().size()) {
                break;
            }
        }
        //at end of this may have more than one rule for each so could either keep all just as back up or can compare and discard worst ones (shortest + most unknown (##))
        //atm think i should keep them all as may be helpful

        return new Wrapper(domain, head, tail, aggregatedRuleList);
    }

    private Rule aggregateRuleList(List<Rule> ruleList) {
        // can have multiple potential rules for same feature in same domain going in here
        //this will try and get the rule that matches all and tests, if doesnt work could potentially move to next set of rules for that feature 
        //(this is where web page contains same bit of info in different places)
        //return null if cant create a rule for it
        List<String> leftList = new ArrayList<>();
        List<String> rightList = new ArrayList<>();
        List<String> openList = new ArrayList<>();
        List<String> closeList = new ArrayList<>();

        for (int i = 0; i < ruleList.size(); i++) {
            leftList.add(ruleList.get(i).getLeft());
            rightList.add(ruleList.get(i).getRight());
            openList.add(ruleList.get(i).getOpen());
            closeList.add(ruleList.get(i).getClose());
        }
        FeatureEnum feature = ruleList.get(0).getFeatureName();
        String left = compareLR(leftList, true, NUM_OF_CHARS);
        String right = compareLR(rightList, true, NUM_OF_CHARS);
        String open = "";
        String close = "";

        return new Rule(feature, open, close, left, right);
    }
    // </editor-fold>
    // <editor-fold desc="String comparison">

    public String compareStrings(List<String> stringList) {
        //this is where the comparison will take place
        //will find a common rule that can be applied to all
        //will do this for all rules
        //take into accoutn that brackets, quotes and < etc will be in pairs so can use this logic when stuff stops matching

        return "";
    }

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
                    stringList.set(i, stringList.get(i).substring(1));
                }
            } else {
                //if hasnt matched then have to assume that there is an interchangeable piece of info here
                char c = compareToCharArray(aggregated.charAt(aggregated.length() - 1), isReverse);
                if (c != ' ' && (c1 = compareToCharArray(aggregated, isReverse)) != ' ') {
                    for (int j = 0; j < stringList.size(); j++) {
                        String rule = stringList.get(j);
                        int index = rule.indexOf(j);
                        if (index > -1) {
                            stringList.set(j, rule.substring(index));
                        } else {
                            break outerloop;
                        }
                    }
                    aggregated += MODIFIER_STRING;
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
        int min = isReverse ? 4 : 0;
        int max = isReverse ? 10 : 6;
        for (int i = min; i < max; i++) {
            if (CHARS_TO_CHECK[i] == character) {
                if (i == 4 || i == 5) {
                    return CHARS_TO_CHECK[i];
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
        int min = isReverse ? 4 : 0;
        int max = isReverse ? 10 : 6;
        for (int i = string.length() - 1; i >= 0; i--) {
            char character = string.charAt(i);
            for (int j = min; j < max; j++) {
                if (CHARS_TO_CHECK[i] == character) {
                    if (j == 4 || j == 5) {
                        //need to check has odd number of this symbol in the list so far else wont work, make sure is an opening 
                        if (i == 0 || getNumOfOccurances(string, CHARS_TO_CHECK[j]) % 2 == 1) //return CHARS_TO_CHECK[j]; 
                        //atm have doubts as what if the content is in the middle of some quotes, wont know what is inside or outside
                        //might be fine for the other method
                        {
                            return ' ';
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
    // </editor-fold>
    // <editor-fold desc="Testing ">
    public void testSystem() {

    }
    
    public boolean testRule(Rule rule){
        
        
        return true;
    }

    // </editor-fold>
    // <editor-fold desc="Adding Test/training data ">
    public void addFile(InputStream inputStream) {
        trainingData = readDataFromFile(inputStream);
    }

    public List<SiteFeatures> getFile() {
        return trainingData;
    }

    public void addFromDB(List<SiteFeatures> siteFeatureList) {
        trainingData = siteFeatureList;
    }

    private List<SiteFeatures> readDataFromFile(InputStream inputStream) {
        List<SiteFeatures> siteList = new ArrayList<SiteFeatures>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                siteList.add(new SiteFeatures(line));
            }
        } catch (Exception e) {

        }
        return siteList;
    }

    // </editor-fold>
}
