/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.SearchEngine.BingSearch;
import Models.SearchEngine.GoogleResults.Result;
import Models.SearchEngine.GoogleSearch;
import Models.SearchEngine.YahooSearch;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kyran
 */
public class SearchResultMiner {
    
    private GoogleSearch googleSearch;
    private BingSearch bingSearch;
    private YahooSearch yahooSearch;

    public SearchResultMiner() {
        googleSearch = new GoogleSearch();
        bingSearch = new BingSearch();
        yahooSearch = new YahooSearch();
    }

    public List<String> getSearchResults(String search) {
        List<String> urlList = new ArrayList<>();

        urlList.addAll(getGoogleResults(search + " tickets"));
        urlList.addAll(getYahooResults(search + " tickets"));
        urlList.addAll(getBingResults(search + " tickets"));

        urlList = aggregateResults(urlList);

        return urlList;
    }

    private List<String> getGoogleResults(String search) {
        List<String> urlList = new ArrayList<>();

        return googleSearch.getResultsJSoup(search);
        
//        List<Result> results = googleSearch.getResults(search);
//
//        if (results != null) {
//            for (int i = 0; i < results.size(); i++) {
//                if (!results.get(i).getUrl().isEmpty()) {
//                    urlList.add(results.get(i).getUrl());
//                }
//            }
//        }
//
//        return urlList;
    }

    private List<String> getYahooResults(String search) {
        List<String> urlList = new ArrayList<>();

        return urlList;
    }

    private List<String> getBingResults(String search) {
        List<String> urlList = new ArrayList<>();

        urlList = bingSearch.getURLs(search);
        
        return urlList;
    }

    private List<String> aggregateResults(List<String> list) {
        //this makes sure only unique entries are passed through
        List<String> urlList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (!urlList.contains(list.get(i))) {
                urlList.add(list.get(i));
            }
        }

        return urlList;
    }
}
