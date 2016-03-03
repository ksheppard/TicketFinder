/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Structures;

import java.util.List;

/**
 *
 * @author Kyran
 */
public class TestResult {
    private String domain;
    private String url;
    private int numFeaturesCorrect;
    private int totalFeatures;
    private List<TestFeature> testFeatureList;

    public TestResult(String domain, String url, List<TestFeature> testFeatureList) {
        this.domain = domain;
        this.url = url;
        this.testFeatureList = testFeatureList;
        totalFeatures = testFeatureList.size();
        
        numFeaturesCorrect = 0;
        for (int i = 0; i < totalFeatures; i++) {
            if(testFeatureList.get(i).isCorrect()) numFeaturesCorrect++;
        }
    }

    public String getDomain() {
        return domain;
    }

    public String getUrl() {
        return url;
    }
    
    

    public int getNumFeaturesCorrect() {
        return numFeaturesCorrect;
    }

    public int getTotalFeatures() {
        return totalFeatures;
    }

    public List<TestFeature> getTestFeatureList() {
        return testFeatureList;
    }
    
    
    
    
    
}
