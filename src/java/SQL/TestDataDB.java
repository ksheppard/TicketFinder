/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.SiteFeatures;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Kyran
 */
public class TestDataDB {
     private Connection conn;

    public TestDataDB(Connection conn) {
        this.conn = conn;
    }
    
    public void addTestData(List<SiteFeatures> dataList){
        
    }
    
    public List<SiteFeatures> getTestData(String domain){
        return null;
    }
}
