/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.SearchEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Kyran
 */
public class BingSearch {

    final String accountKey = "<iRMFnzR9dSbuNWIJzFoqVy3h0+mRqksiErHiRIgZSSE>";
    final String bingUrlPattern = "https://api.datamarket.azure.com/Bing/Search/Web?Query=%%27%s%%27&$format=JSON";

    public BingSearch() {

    }

    public List<String> getURLs(String search) {
        List<String> urlList = new ArrayList<>();

        String query = "";
        try {
            query = URLEncoder.encode(search, Charset.defaultCharset().name());

            String bingUrl = String.format(bingUrlPattern, query);
            String accountKeyEnc = Base64.encodeBase64String((accountKey + ":" + accountKey).getBytes());
            URL url = new URL(bingUrl);

            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
            final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            final StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            final JSONObject json = new JSONObject(response.toString());
            final JSONObject d = json.getJSONObject("d");
            final JSONArray results = d.getJSONArray("results");
            final int resultsLength = results.length();
            for (int i = 0; i < resultsLength; i++) {
                final JSONObject aResult = results.getJSONObject(i);
                urlList.add(aResult.get("Url").toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(BingSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return urlList;
    }
}
