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


public class DateComparator implements Comparator<SearchResult> {
    @Override
    public int compare(SearchResult o1, SearchResult o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
