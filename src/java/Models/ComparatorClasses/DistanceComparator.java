/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.ComparatorClasses;

import Models.SearchResult;
import java.util.Comparator;

/**
 *
 * @author Kyran
 */


public class DistanceComparator implements Comparator<SearchResult> {
    @Override
    public int compare(SearchResult o1, SearchResult o2) {
        if(o1.getDistance() > o2.getDistance()) return 1;
        else if(o2.getDistance() > o1.getDistance()) return -1;
        else return 0;
    }
}