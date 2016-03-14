/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.SearchEngine;

import Models.SearchEngine.GoogleResults.ResponseData;
import Models.SearchEngine.GoogleResults.Result;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Kyran
 */
public class GoogleSearch {

    public GoogleSearch() {

    }

    public List<Result> getResults(String search) {

        String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
        String charset = "UTF-8";

        List<Result> resultslist = null;
        URL url;
        try {
            url = new URL(google + URLEncoder.encode(search, charset));

            Reader reader = new InputStreamReader(url.openStream(), charset);
            GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);

            ResponseData rd = results.getResponseData();

            if (rd == null) {
                System.out.println("response data was null");
                return null;
            }
            resultslist = rd.getResults();

//            for (int i = 0; i < resultslist.size(); i++) {
//                System.out.println(resultslist.get(i).getTitle());
//                System.out.println(resultslist.get(i).getUrl());
//                System.out.println("");
//            }
            // Show title and URL of 1st result.
        } catch (Exception ex) {
            Logger.getLogger(GoogleSearch.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getStackTrace().toString());
            System.out.println();
        }
        return resultslist;

    }

    public List<String> getResultsJSoup(String search) {
        List<String> urlList = new ArrayList<>();
        
        try {
            String google = "http://www.google.com/search?num=30&q=";
            String charset = "UTF-8";
            String userAgent = "TicketFinder 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
            
            Elements links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select(".g>.r>a");
            
            for (Element link : links) {
                String title = link.text();
                String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
                url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");
                
                if (!url.startsWith("http")) {
                    continue; // Ads/news/etc.
                }
//                System.out.println("Title: " + title);
//                System.out.println("URL: " + url);
                urlList.add(url);
                
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GoogleSearch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GoogleSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return urlList;
    }

}
