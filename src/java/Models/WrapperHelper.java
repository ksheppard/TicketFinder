/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kyran
 */
public class WrapperHelper {

    public WrapperHelper() {
    }

    public void trainSystem(InputStream inputStream) {
        List<SiteFeatures> siteList = getDataFromFile(inputStream);
        
        
    }

    public void testSystem() {

    }

    public List<SiteFeatures> getDataFromFile(InputStream inputStream) {
        List<SiteFeatures> siteList = new ArrayList<SiteFeatures>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                siteList.add(new SiteFeatures(line));
            }
        } catch (Exception e) {
            
        }
        return siteList;
    }

}
