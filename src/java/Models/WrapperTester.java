/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Enums.FeatureEnum;
import SQL.TestDataDB;
import SQL.WrapperDB;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kyran
 */
public class WrapperTester {

    WrapperDB wrapperDB;
    WrapperExecutor wrapperExecutor;
    TestDataDB testDataDB;

    public WrapperTester(Connection conn) {
        wrapperDB = new WrapperDB(conn);
        testDataDB = new TestDataDB(conn);
        wrapperExecutor = new WrapperExecutor();
    }

    public WrapperTester() {
        //this is used by the WrapperTrainer as doesnt need SQL access, just method access
        wrapperExecutor = new WrapperExecutor();
    }
    
    public List<TestResult> performIndTestsFromDB(String domain) {
        
        return performIndTests(testDataDB.getIndTestData(domain));
    }

    public List<TestResult> performIndTests(List<SiteFeatures> siteFeaturesList) {
        List<TestResult> testResultList = new ArrayList<>();
        WebPageManager wpManager = new WebPageManager();
        //get all wrappers that need retrieving from db
        Map<String, Wrapper> wrapperMap = getWrappers(siteFeaturesList);

        for (int i = 0; i < siteFeaturesList.size(); i++) {
            SiteFeatures sf = siteFeaturesList.get(i);
            testResultList.add(testIndWrapper( wrapperMap.get(sf.getDomain()), wpManager.getHTML(sf.getUrl()), sf));
        }

        testDataDB.addTestData(siteFeaturesList);
        
        return testResultList;
    }

    private Map<String, Wrapper> getWrappers(List<SiteFeatures> siteFeaturesList) {
        //retrieves all wrappers needed to run tests, returns as map using key of domain and value as the wrapper object
        List<String> uniqueDomains = new ArrayList<String>();
        Map<String, Wrapper> wrapperMap = new HashMap<>();

        for (int i = 0; i < siteFeaturesList.size(); i++) {
            if (!uniqueDomains.contains(siteFeaturesList.get(i).getDomain())) {
                uniqueDomains.add(siteFeaturesList.get(i).getDomain());
            }
        }

        for (int i = 0; i < uniqueDomains.size(); i++) {
            wrapperMap.put(uniqueDomains.get(i), wrapperDB.getWrapper(uniqueDomains.get(i), 0));
        }

        return wrapperMap;
    }

    private TestResult testIndWrapper(Wrapper wrapper, String html, SiteFeatures siteFeatures) {
        List<TestFeature> testFeatureList = new ArrayList();

        //perform test like from other method in wrapperhelper
        for (FeatureEnum feature : FeatureEnum.values()) {
            if(feature == FeatureEnum.URL) continue;
            List<Rule> filteredRules = wrapper.filterRules(feature);

            if (filteredRules != null) {
                //go through all rules saved for each feature
                for (int i = 0; i < filteredRules.size(); i++) {
                    String val = getValFromRule(html, filteredRules.get(i));
                    //if finds a value or is last rule then input result, else go to next rule
                    if(val != "" || i == filteredRules.size() - 1){
                        testFeatureList.add(new TestFeature(feature, siteFeatures.getValue(feature), val));
                    break;
                    }
                }
            } else {
                //if null display this in test
                testFeatureList.add(new TestFeature(feature, siteFeatures.getValue(feature), "NO RULE"));
            }
        }

        return new TestResult(siteFeatures.getDomain(), siteFeatures.getUrl(), testFeatureList);
    }

    //WILL BE BROKEN UP AND MOVED INTO WRAPPER EXECUTOR?
    public String getValFromRule(String html, Rule rule) {
        return wrapperExecutor.getValFromRule(html, rule);
    }

}
