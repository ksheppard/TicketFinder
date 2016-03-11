/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.Structures.ErrorReport;
import Models.Structures.WrapperlessDomain;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kyran
 */
public class WrapperlessDomainDB {
    private Connection conn;

    public WrapperlessDomainDB(Connection conn) {
        this.conn = conn;
    }
    
    public boolean addDomain(WrapperlessDomain wd){
        if(checkExists(wd.getDomain())){
            return incrementHitCount(wd.getDomain());
        }
        else{
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("INSERT into WrapperlessDomains values ('%s', '%d', %b)", 
                    wd.getDomain(), wd.getHitCount(), wd.isChecked()));

            state.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return false;
        }
        
        return true;
        }
    }
    
    private boolean checkExists(String domain){
        boolean found = false;  
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from WrapperlessDomains WHERE `Domain` = '%s'", domain));
            while (rs.next()) {
                found  = true;
                break;
                               
            }

            state.close();
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return false;
        }
        
        return found;
    }
    
    public boolean incrementHitCount(String domain){
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("UPDATE WrapperlessDomains SET HitCount = (HitCount + 1) WHERE `Domain` = %s"
                    , domain));

            state.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return false;
        }
        
        return true;
    }
    
    public boolean changeCheckedStatus(WrapperlessDomain wd){
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("UPDATE WrapperlessDomains SET Checked = %b WHERE Id = %d"
                    , !wd.isChecked(), wd.getId()));

            state.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return false;
        }
        
        return true;
    }
    
    public boolean deleteDomain(WrapperlessDomain wd){
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("DELETE FROM WrapperlessDomains WHERE Id = %d"
                    , wd.getId()));

            state.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return false;
        }
        
        return true;
    }
    
    public List<WrapperlessDomain> getDomains(){
        
        List<WrapperlessDomain> list = new ArrayList<>();
        
         try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from WrapperlessDomains"));
            while (rs.next()) {

                int id = rs.getInt("Id");
                String domain = rs.getString("Domain");
                int hitCount = rs.getInt("HitCount");
                boolean checked = rs.getBoolean("Checked");

                list.add(new WrapperlessDomain(id, domain, checked, hitCount));                
            }

            state.close();
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return null;
        }
         
        return list;
    }
    
}
