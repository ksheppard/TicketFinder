/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Structures.SearchResult;
import Models.ComparatorClasses.AZComparator;
import Models.ComparatorClasses.DateComparator;
import Models.ComparatorClasses.DistanceComparator;
import Models.Enums.SortingOptionsEnum;
import Models.Structures.SearchFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    
    public List<SearchResult> filterResults(SearchFilter filter, List<SearchResult> results) {

        List<SearchResult> filteredResults = results;
        
        if(filter.getDistance() != -1 && !filter.getLocation().isEmpty()){
            filteredResults = filterLocation(filter.getLocation(), filter.getDistance(), results);
        }
        if(filter.getStartDate() != null || filter.getEndDate() != null){
            filteredResults = filterDate(filter.getStartDate(), filter.getEndDate(), results);
        }
        if(filter.getTicketProviders() != null && filter.getTicketProviders().size() > 0){
            filteredResults = filterProviders(results, filter.getTicketProviders());
        }
        
        return filteredResults;
    }
    
    private List<SearchResult> filterLocation(String location, int distance, List<SearchResult> results){
        List<SearchResult> filteredResults = new ArrayList<>();
        
        //this needs doing once have implemented how will calculate the location/distance
        
        return filteredResults;
    }
    
    private List<SearchResult> filterDate(Date startDate, Date endDate, List<SearchResult> results){
        List<SearchResult> filteredResults = new ArrayList<>();
        Date d;
        for (int i = 0; i < results.size(); i++) {
            d = results.get(i).getDate();
            if((startDate == null || startDate.before(d))
                    && (endDate == null || endDate.after(d))){
                filteredResults.add(results.get(i));
            }
        }
        
        return filteredResults;
    }
    
    private List<SearchResult> filterProviders( List<SearchResult> results, List<String> ticketProviders){
        List<SearchResult> filteredResults = new ArrayList<>();
        SearchResult sr;
        for (int i = 0; i < results.size(); i++) {
            sr = results.get(i);
            sr.clearSites();
            for (int j = 0; j < results.get(i).getSiteFeatures().size(); j++) {
                if(ticketProviders.contains(results.get(i).getSiteFeatures().get(j).getDomain())){
                    sr.addSite(results.get(i).getSiteFeatures().get(j));
                }
            }
            if(sr.getSiteFeatures().size() > 0){
                filteredResults.add(sr);
            }
        }
        
        return filteredResults;
    }
    
    // </editor-fold>
    
    
}
