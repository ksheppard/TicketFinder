/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

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

    String domain;
    String url;
    Map<FeatureEnum, String> featureMap;

    public SiteFeatures(String line) {
        initMap();
        String[] info = line.split(",,");
        domain = info[0];
        url = info[1];

        try {
            int i = 2;
            for (FeatureEnum feat : FeatureEnum.values()) {
                featureMap.put(feat, info[i]);
                i++;
                if(i == info.length) break;
            }
        } catch (Exception e) {
            //this is to catch a possible out of bounds exception when more info added to enum
        }

    }

    private void initMap() {
        featureMap = new HashMap<FeatureEnum, String>();
        for (FeatureEnum feat : FeatureEnum.values()) {
            featureMap.put(feat, "");
        }
    }
}
