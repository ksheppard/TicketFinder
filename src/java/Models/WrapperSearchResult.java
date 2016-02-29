/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Enums.FeatureEnum;

/**
 *
 * @author Kyran
 */
public class WrapperSearchResult {
    //used to store the data that find in html, holds the position of each
    
    private int startIndex;
    private String value;
    private FeatureEnum feature;

    public WrapperSearchResult(int startIndex, String value, FeatureEnum feature) {
        this.startIndex = startIndex;
        this.value = value;
        this.feature = feature;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FeatureEnum getFeature() {
        return feature;
    }

    public void setFeature(FeatureEnum feature) {
        this.feature = feature;
    }
    
    
}
