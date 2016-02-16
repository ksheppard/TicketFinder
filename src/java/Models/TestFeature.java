/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Kyran
 */
public class TestFeature {
    
    private FeatureEnum FeatureName;
    private String expectedResult;
    private String actualResult;
    private boolean correct;

    public TestFeature(FeatureEnum FeatureName, String expectedResult, String actualResult) {
        this.FeatureName = FeatureName;
        this.expectedResult = expectedResult;
        this.actualResult = actualResult;
        correct = actualResult.equals(expectedResult);
    }
    
    public FeatureEnum getFeatureName() {
        return FeatureName;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public String getActualResult() {
        return actualResult;
    }

    public boolean isCorrect() {
        return correct;
    }
    
    
}
