/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.Enums.FeatureEnum;
import Models.Structures.SiteFeatures;
import Models.Structures.TicketListFeatures;
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
    
    public boolean addIndTestData(List<SiteFeatures> dataList){
        try {
            Statement state = conn.createStatement();
            
            String statement = "INSERT INTO testdata VALUES ";
            
            for (int i = 0; i < dataList.size(); i++) {
                statement += String.format("('%s', '%s', 0)", dataList.get(i).getDomain(), dataList.get(i).getUrl());
                
                if(i != dataList.size() - 1) statement += " , ";
            }
            
            state.executeUpdate(statement);
            statement = "INSERT INTO testdatafeatures VALUES ";
            
            for (int i = 0; i < dataList.size(); i++) {
                for(FeatureEnum feature : FeatureEnum.values()){
                    if(dataList.get(i).getFeatureMap().containsKey(feature)){
                        statement += String.format("('%d', '%s', '%s') , ", dataList.get(i).getId(), feature, dataList.get(i).getValue(feature));
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
        
        
        //need to play around with joins
        List<SiteFeatures> dataList = new ArrayList<>();
//         
        try {
            List<SiteFeatures> tempDataList = new ArrayList<>();
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from testdata WHERE `domain` = '%s' AND `Type` = %d", domain, 0));
            while (rs.next()) {
                tempDataList.add(new SiteFeatures(rs.getString("Domain"), rs.getString("Url"), rs.getInt("id")));
            }
            
            for (int i = 0; i < tempDataList.size(); i++) {
                SiteFeatures feature = tempDataList.get(i);
                rs = state.executeQuery(String.format("SELECT * from testdatafeatures WHERE `T_id` = '%d'", feature.getId()));
                
                while (rs.next()) {
                    FeatureEnum name = FeatureEnum.valueOf(rs.getString("FeatureName"));
                    String val = rs.getString("FeatureValue");
                    feature.addFeature(name, val);
                }
                
                dataList.add(feature);
            }

            state.close();
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        
        return dataList;
    }
    
    public boolean addListTestData(List<TicketListFeatures> dataList){
        try {
            Statement state = conn.createStatement();
            
            String statement = "INSERT INTO testdata VALUES ";
            
            for (int i = 0; i < dataList.size(); i++) {
                statement += String.format("('%s', '%s', 1)", dataList.get(i).getDomain(), dataList.get(i).getUrl());
                
                if(i != dataList.size() - 1) statement += " , ";
            }
            
            state.executeUpdate(statement);
            statement = "INSERT INTO testdatafeatures VALUES ";
            
            for (int i = 0; i < dataList.size(); i++) {
                for (int j = 0; j < dataList.get(i).getUrlList().size(); j++) {
                    statement += String.format("('%d', '%s', '%s') , ", dataList.get(i).getId(), FeatureEnum.URL.toString(), dataList.get(i).getUrlList().get(j));
                }
            }
            statement = statement.substring(0, statement.length() - 3);
            
            state.close();

        } catch (SQLException e) {
            return false;
        }
        
        return false;
    }
    
    public List<TicketListFeatures> getListTestData(String domain){
        List<TicketListFeatures> dataList = new ArrayList<>();
        
        try {
            List<TicketListFeatures> tempDataList = new ArrayList<>();
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from testdata  WHERE `domain` = '%s' AND `Type` = %d", domain, 1));
            while (rs.next()) {
                tempDataList.add(new TicketListFeatures(rs.getInt("id"), rs.getString("Domain"), rs.getString("Url")));
            }
            
            for (int i = 0; i < tempDataList.size(); i++) {
                TicketListFeatures feature = tempDataList.get(i);
                rs = state.executeQuery(String.format("SELECT * from testdatafeatures WHERE `T_id` = '%d'", feature.getId()));
                
                while (rs.next()) {
                    FeatureEnum name = FeatureEnum.valueOf(rs.getString("FeatureName"));
                    String val = rs.getString("FeatureValue");
                    feature.addUrl(val);
                }
                
                dataList.add(feature);
            }

            state.close();
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        
        return dataList;
    }
}
