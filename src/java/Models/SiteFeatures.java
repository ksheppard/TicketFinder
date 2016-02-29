/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Enums.FeatureEnum;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Hannah
 */
public class SiteFeatures {

    private String domain;
    private String url;
    private Map<FeatureEnum, String> featureMap;

    public SiteFeatures(String line) {
        initMap();
        String[] info = line.split(",,");
        domain = info[0].trim();
        url = info[1].trim();

        try {
            int i = 2;
            for (FeatureEnum feat : FeatureEnum.values()) {
                featureMap.put(feat, info[i].trim());
                i++;
                if(i == info.length) break;
            }
        } catch (Exception e) {
            //this is to catch a possible out of bounds exception when more info added to enum
        }

    }

    public SiteFeatures(String domain, String url) {
        this.domain = domain;
        this.url = url;
    }
    
    
    
    public String getValue(FeatureEnum feature){
        return featureMap.get(feature);
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

    public Map<FeatureEnum, String> getFeatureMap() {
        return featureMap;
    }

    public void setFeatureMap(Map<FeatureEnum, String> featureMap) {
        this.featureMap = featureMap;
    }

    
    private void initMap() {
        featureMap = new HashMap<FeatureEnum, String>();
        for (FeatureEnum feat : FeatureEnum.values()) {
            featureMap.put(feat, "");
        }
    }
}
