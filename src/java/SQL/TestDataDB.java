/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.Enums.FeatureEnum;
import Models.Structures.SiteFeatures;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    
    public boolean addTestData(List<SiteFeatures> dataList){
        try {
            Statement state = conn.createStatement();
            
            String statement = "INSERT INTO testdata VALUES ";
            
            for (int i = 0; i < dataList.size(); i++) {
                statement += String.format("('%s', '%s')", dataList.get(i).getDomain(), dataList.get(i).getUrl());
                
                if(i != dataList.size() - 1) statement += " , ";
            }
            
            state.executeUpdate(statement);
            statement = "INSERT INTO testdatafeatures VALUES ";
            
            for (int i = 0; i < dataList.size(); i++) {
                for(FeatureEnum feature : FeatureEnum.values()){
                    if(dataList.get(i).getFeatureMap().containsKey(feature)){
                        statement += String.format("('%s', '%s', '%s') , ", dataList.get(i).getUrl(), feature, dataList.get(i).getValue(feature));
                    }
                }
                
            }
            statement = statement.substring(0, statement.length() - 3);
            
            state.close();

        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    public List<SiteFeatures> getIndTestData(String domain){
        List<SiteFeatures> dataList = new ArrayList<>();
        
        //need to play around with joins

//         
//        try {
//            Statement state = conn.createStatement();
//            ResultSet rs = state.executeQuery(String.format("SELECT * from testdata WHERE domain = '%s'", domain));
//            while (rs.next()) {
//                dataList.add(new SiteFeatures(rs.getString(1), rs.getString(2)));
//            }
//            
//            for (int i = 0; i < dataList.size(); i++) {
//                rs = state.executeQuery(String.format("SELECT * from testdatafeatures WHERE url = '%s'", dataList.get(i).getUrl()));
//            }
//
//            state.close();
//            rs.close();
//
//        } catch (SQLException e) {
//            System.err.println("Error: " + e);
   //     }
        
        return dataList;
    }
}
