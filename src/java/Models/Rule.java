/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Hannah
 */
public class Rule {
    private FeatureEnum featureName;
    private String open;
    private String close;
    private String left;
    private String right;

    public Rule(FeatureEnum FeatureName) {
        this.featureName = FeatureName;
    }

    public Rule(FeatureEnum FeatureName, String Open, String Close, String Left, String Right) {
        this.featureName = FeatureName;
        this.open = Open;
        this.close = Close;
        this.left = Left;
        this.right = Right;
    }

    public FeatureEnum getFeatureName() {
        return featureName;
    }

    public void setFeatureName(FeatureEnum FeatureName) {
        this.featureName = FeatureName;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String Open) {
        this.open = Open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String Close) {
        this.close = Close;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String Left) {
        this.left = Left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String Right) {
        this.right = Right;
    }

   
    
    
    
}
