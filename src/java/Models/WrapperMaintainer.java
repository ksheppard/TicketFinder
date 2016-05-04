/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Structures.SiteFeatures;
import Models.Structures.TicketListFeatures;
import SQL.TestDataDB;
import SQL.WrapperDB;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Kyran
 */
public class WrapperMaintainer {

    TestDataDB testDataDB;
    
    WrapperTrainer wrapperTrainer;
    
    public WrapperMaintainer(Connection conn) {
        testDataDB = new TestDataDB(conn);
        wrapperTrainer = new WrapperTrainer(conn);
    }
    
    public boolean repairIndividualWrapper(String domain){
        
        List<SiteFeatures> testData = testDataDB.getIndTestData(domain);
        
        if(testData == null || testData.size() < 3){
            //not enough to retrain reliably
            return false;
        }
        
        return wrapperTrainer.trainIndividual(testData);

    }
   
    public boolean repairListWrapper(String domain){
        
        List<TicketListFeatures> testData = testDataDB.getListTestData(domain);
        
        if(testData == null || testData.size() < 3){
            //not enough to retrain reliably
            return false;
        }
        
        return wrapperTrainer.trainLists(testData);

    }
    
}
