/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.ComparatorClasses.AZComparator;
import Models.ComparatorClasses.DateComparator;
import Models.ComparatorClasses.DistanceComparator;
import Models.Enums.SortingOptionsEnum;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Kyran
 */
public class ResultFilter {

    public ResultFilter() {

    }
    
    // <editor-fold  desc=sorting">

    public List<SearchResult> sortResults(SortingOptionsEnum sortOption, List<SearchResult> results, String location) {
        
        switch (sortOption) {
            case Az:
                return sortAlphabetically(results, true);
            case Date:
                return sortDate(results);
            case Distance:
                return sortDistance(results, location);
            case Za:
                return sortAlphabetically(results, false);
        }
        
        return results;
    }
    
    private List<SearchResult> sortAlphabetically(List<SearchResult> results, boolean aToZ){
        List<SearchResult> sortedList = results;
        
        Collections.sort(sortedList, new AZComparator());
        
        if(!aToZ) Collections.reverse(sortedList);
        
        return sortedList;
    }
    
    private List<SearchResult> sortDate(List<SearchResult> results){
        List<SearchResult> sortedList = results;
        
        Collections.sort(sortedList, new DateComparator());
        
        return sortedList;
    }
    
    private List<SearchResult> sortDistance(List<SearchResult> results, String location){
        List<SearchResult> sortedList = results;
        
        //go through list and set all distance attributes from the location
        
        Collections.sort(sortedList, new DistanceComparator());
        
        return sortedList;
    }
    
    // </editor-fold>

    // <editor-fold  desc=sorting">
    
    public void filterResults() {

    }
    
    // </editor-fold>
    
    
}
