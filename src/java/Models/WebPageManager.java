/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Kyran
 */
public class WebPageManager {
    //handles all JSOUP related code

    public WebPageManager() {
    }
    
    public String getHTML(String url){
        try{
            Document doc = Jsoup.connect(url).get();
            return doc.body().toString();
        }
        catch(IOException e){
            //returns empty if cant connect to the web page
            return "";
        }
    }
}
