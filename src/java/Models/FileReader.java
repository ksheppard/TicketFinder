/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Structures.SiteFeatures;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kyran
 */
public class FileReader {
    public FileReader(){
        
    }
    
    public List<SiteFeatures> readIndDataFromFile(InputStream inputStream) {
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
    
    public List<TicketListFeatures> readListDataFromFile(InputStream inputStream) {
        List<TicketListFeatures> siteList = new ArrayList<TicketListFeatures>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                siteList.add(new TicketListFeatures(line));
            }
        } catch (Exception e) {

        }
        return siteList;
    }
}
