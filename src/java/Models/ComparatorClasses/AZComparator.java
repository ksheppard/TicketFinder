/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.ComparatorClasses;

import Models.Structures.SearchResult;
import java.util.Comparator;

/**
 *
 * @author Kyran
 */


public class AZComparator implements Comparator<SearchResult> {
    @Override
    public int compare(SearchResult o1, SearchResult o2) {
        return o1.getArtist().compareTo(o2.getArtist());
    }
}