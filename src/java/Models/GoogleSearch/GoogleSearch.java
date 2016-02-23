/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.GoogleSearch;

import Models.GoogleSearch.GoogleResults.ResponseData;
import Models.GoogleSearch.GoogleResults.Result;
import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

            for (int i = 0; i < resultslist.size(); i++) {
                System.out.println(resultslist.get(i).getTitle());
                System.out.println(resultslist.get(i).getUrl());
                System.out.println("");
            }

            // Show title and URL of 1st result.
        } catch (Exception ex) {
            Logger.getLogger(GoogleSearch.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getStackTrace().toString());
            System.out.println();
        }
        return resultslist;

    }

}
