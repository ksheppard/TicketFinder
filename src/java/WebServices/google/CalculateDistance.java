/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices.google;

import Models.gDistanceObjects.Element;
import Models.gDistanceObjects.GoogleDistanceObject;
import Models.gDistanceObjects.Distance_;
import Models.gDistanceObjects.Row;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URLEncoder;
import java.util.List;

/**
 *
 * @author Jarrett
 */
public class CalculateDistance {

    private static final String APIKEY = "AIzaSyDpa35XC9Uru4GyIpej3ayzBRW4lfU6yig";
    private static String jsonResponse = "";

    public static Distance calculate(String address1, String address2) {
        String requestURL = createURL(address1, address2);

        jsonResponse = executeGet(requestURL);
        Gson gson = new GsonBuilder().create();

        try {
            GoogleDistanceObject gDistanceObject = gson.fromJson(jsonResponse, GoogleDistanceObject.class);

            if (gDistanceObject == null) {
                return new Distance("", 0);
            } else {
                Row row = gDistanceObject.getRows().get(0);
                Element element = row.getElements().get(0);
                Distance_ distanceElement = element.getDistance();

                return new Distance(distanceElement.getText(), distanceElement.getValue());
            }
        } catch (Exception e) {
            System.out.println("Error in obtaining address");
            return new Distance("", 0);
        }

    }

    private static String createURL(String address1, String address2) {
        try {
            String requestURL;
            String baseURL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
            String address1Encoded = URLEncoder.encode(address1, "UTF-8");
            String address2Encoded = URLEncoder.encode(address2, "UTF-8");

            requestURL = baseURL + address1Encoded + "&destinations="
                    + address2Encoded + "&mode=driving&language=en-US&key=" + APIKEY;

            return requestURL;
        } catch (Exception e) {
            System.out.println("Exception Occured");
        }

        return null;
    }

    private static String executeGet(String targetURL) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("\nSending GET request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
