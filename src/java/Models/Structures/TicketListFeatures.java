/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Structures;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kyran
 */
public class TicketListFeatures {
    private int id;
    private String domain;
    private String url;
    private List<String> urlList;

    public TicketListFeatures(String domain, String url, List<String> urlList) {
        this.domain = domain;
        this.url = url;
        this.urlList = urlList;
        this.id = id;
    }

    public TicketListFeatures(int id, String domain, String url) {
        this.id = id;
        this.domain = domain;
        this.url = url;
        urlList = new ArrayList<>();
    }
    
    

    public TicketListFeatures(String line) {
        
        String[] info = line.split(",,");
        domain = info[0].trim();
        url = info[1].trim();
        urlList = new ArrayList<>();

        try {
            for (int j = 2; j < info.length; j++) {
                urlList.add(info[j]);
            }
        } catch (Exception e) {
            //this is to catch a possible out of bounds exception when more info added to enum
        }
        
        
    }
    
    public void addUrl(String link){
        urlList.add(link);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }
    
    
}
