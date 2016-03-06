/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Structures;

import Models.Enums.FeatureEnum;
import Models.WrapperSearchResult;
import java.util.List;

/**
 *
 * @author Hannah
 */
public class Rule {
    private static final int NUM_OF_CHARS = 150;
    private static final int HEAD_TAIL_CHARS = NUM_OF_CHARS * 5;
    
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
    
    public void setLeft(WrapperSearchResult searchResult, List<WrapperSearchResult> searchList, String html) {
        this.left = html.substring(searchResult.getStartIndex() - 1 - NUM_OF_CHARS, searchResult.getStartIndex() - 1);
    }
    public String getRight() {
        return right;
    }

    public void setRight(String Right) {
        this.right = Right;
    }
    
    public void setRight(WrapperSearchResult searchResult, List<WrapperSearchResult> searchList, String html) {
        this.right = html.substring(searchResult.getStartIndex() + searchResult.getValue().length(), searchResult.getStartIndex() + searchResult.getValue().length() + NUM_OF_CHARS);
    }

//    public void setClose(WrapperSearchResult searchResult, String html) {
//        this.open = html.substring(searchResult.getStartIndex() - 1 - NUM_OF_CHARS - HEAD_TAIL_CHARS, searchResult.getStartIndex() - 1 - NUM_OF_CHARS);
//    }
//
//    public void setOpen(WrapperSearchResult searchResult, String html) {
//         this.close = html.substring(searchResult.getStartIndex() + searchResult.getValue().length() + NUM_OF_CHARS, searchResult.getStartIndex() + searchResult.getValue().length() + NUM_OF_CHARS + HEAD_TAIL_CHARS);
//    }


   
    
    
    
}
