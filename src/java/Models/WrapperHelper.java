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
    
    public WrapperHelper() {
    }

    public void trainSystem(InputStream inputStream) {
        List<SiteFeatures> siteList = getDataFromFile(inputStream);
        List<Wrapper> wrapperList = new ArrayList<Wrapper>();
        for (int i = 0; i < siteList.size(); i++) {
            //wrapperList.add(generateWrapper(siteList.get(i)));
            wrapperList.add(generateFromText(siteList.get(i)));
        }
       // Wrapper wrapper = aggregateWrappers(wrapperList);
        //save into db at end
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

    private Wrapper generateFromText(SiteFeatures feature) {
        //make so isnt caps sensitive? aka convert everything to lower
        Wrapper wrapper = null;
        html = null;
        try {
            Document doc = Jsoup.connect(feature.getUrl()).get();
            html = doc.body().toString();
            int length = html.length();
            wrapper = createWrapperFromSearch(getDataFromFile(feature));

        } catch (IOException ex) {
            Logger.getLogger(WrapperHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return wrapper;
    }

    private List<WrapperSearchResult> getDataFromFile(SiteFeatures feature) {
        List<WrapperSearchResult> searchList = new ArrayList<WrapperSearchResult>();
        Iterator it = feature.getFeatureMap().entrySet().iterator();
        
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            FeatureEnum featureEnum = FeatureEnum.valueOf(pair.getKey().toString());
            String value = pair.getValue().toString();
            if(value == "") continue;
            it.remove(); // avoids a ConcurrentModificationException
            int index = 0;
            while (true) {
                index = html.indexOf(value, index);
                if (index == -1) {
                    break;
                } else {
                    searchList.add(new WrapperSearchResult(index, value, featureEnum));
                }
                index += value.length();
            }
            System.out.println("");
        }
        return searchList;
    }
    
    private Wrapper createWrapperFromSearch(List<WrapperSearchResult> searchList){
        Wrapper wrapper = null;
        
        for (int i = 0; i < searchList.size(); i++) {
            WrapperSearchResult searchResult = searchList.get(i);
            Rule rule = new Rule(searchResult.getFeature());
            rule.setLeft(setLeft(searchResult, searchList, i));
            rule.setRight(setLeft(searchResult, searchList, i));
            
        }
        
        return wrapper;
    }
    
    private String setLeft(WrapperSearchResult searchResult, List<WrapperSearchResult> searchList, int index){
        //index and rest of list is so can stop when reaches other data?
        //could put this in the setter for left and right within the rule class - do later
        String left = html.substring(searchResult.getStartIndex() - 1 - NUM_OF_CHARS, searchResult.getStartIndex() - 1);
        
        return left;
    }
    
    private String setRight(WrapperSearchResult searchResult, List<WrapperSearchResult> searchList, int index){
        
        String right = html.substring(searchResult.getStartIndex() + searchResult.getValue().length(), searchResult.getStartIndex() + searchResult.getValue().length() + NUM_OF_CHARS);
        
        return right;
    }

    private Rule createRule(FeatureEnum feature, String value, Element element) {

        Rule rule = new Rule(feature);
        int index = element.toString().indexOf(value);
        rule.setLeft(element.toString().substring(0, index - 1));
        rule.setLeft(element.toString().substring(index + value.length()));
        //ignore open and close for now

        return rule;
    }

//    private Elements filterSearchElements(Elements searchElements, String val) {
////        Elements filteredElements = new Elements();
////        for (int i = 0; i < searchElements.size(); i++) {
////            //want to put all elements in to the next stage if possible as aggregation stage can weed out the ones that wont work
////            Element element = searchElements.get(i);
////            String test = element.text();
////            String id = element.id();
////            if(element.text().contains(String.format(">%s<", val))){
////                System.out.println("");
////            }
////            
////        }
////        filteredElements.add(null);
//
////        return filteredElements;
//        return searchElements;
//    }
//
//    private Wrapper aggregateWrappers(List<Wrapper> wrapperList) {
//        //put collection of wrapper with mess of rules in here and then will go from there
//        return null;
//    }
//
//    private List<Rule> aggregateRuleList(List<Rule> ruleList) {
//        // can have multiple potential rules for same feature in same domain going in here
//        //this will try and get the rule that matches all and tests, if doesnt work could potentially move to next set of rules for that feature 
//        //(this is where web page contains same bit of info in different places)
//        return null;
//    }

    public void testSystem() {

    }

    public List<SiteFeatures> getDataFromFile(InputStream inputStream) {
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

}
