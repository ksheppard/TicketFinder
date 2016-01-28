/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.FeatureEnum;
import Models.Rule;
import Models.Wrapper;
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
public class WrapperDB {
    private Connection conn;

    public WrapperDB(Connection conn) {
        this.conn = conn;
    }
    
    public Wrapper getWrapper(String domain){
        List<Rule> ruleList = getRules(domain);
        String domainVal = null;
        String head = null;
        String tail = null;
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from headtail WHERE domain = '%s'", domain));
            while (rs.next()) {
                
                domainVal = rs.getString(1).trim();
                head = rs.getString(2).trim();
                tail = rs.getString(3).trim();
            }

            state.close();
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        
        return new Wrapper(domainVal, head, tail, ruleList);
    }
    
    public List<Rule> getRules(String domain){
        //return all rules from a specified domain
        //for use in testing system and for user queries
        List<Rule> ruleList = new ArrayList<Rule>();
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from extractionrules WHERE domain = '%s'", domain));
            while (rs.next()) {
                //domain is column 1
                FeatureEnum feature = FeatureEnum.valueOf(rs.getString(2).trim());
                String open = rs.getString(3).trim();
                String close = rs.getString(4).trim();
                String left = rs.getString(5).trim();
                String right = rs.getString(6).trim();
                ruleList.add(new Rule(feature, open, close, left, right));
            }

            state.close();
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        
        return ruleList;
    }
    
    public boolean addWrapper(Wrapper wrapper){
        if(checkDomainExists(wrapper.getDomain())) removeRules(wrapper.getDomain());
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("INSERT INTO extractionrules VALUES ('%s','%s','%s')",wrapper.getDomain(), wrapper.getHead(), wrapper.getTail()));
            state.close();

        } catch (SQLException e) {
            return false;
        }
        return addRules(wrapper.getDomain(), wrapper.getRuleList());
    }
    
    public boolean addRules(String domain, List<Rule> ruleList){
        //to be used for training the system
        
        try {
            Statement state = conn.createStatement();
            String updateString = "INSERT INTO extractionrules VALUES ";
            for (int i = 0; i < ruleList.size(); i++) {
                Rule rule = ruleList.get(i);
                updateString += String.format("('%s','%s','%s','%s','%s','%s')", domain, rule.getFeatureName(), rule.getOpen(), rule.getClose(), rule.getLeft(), rule.getRight());
                if(i < ruleList.size() - 1) updateString += ",";
            }
            state.executeUpdate(updateString);
            state.close();

        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    private boolean checkDomainExists(String domain){
        boolean exists = false;
        
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from headtail WHERE `domain` = '%s'", domain));
            exists = rs.next();
            
            state.close();
            rs.close();
            
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        return exists;
    }
    
    private boolean removeRules(String domain){
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format(
                    "DELETE FROM headtail WHERE `domain`='%s'", domain));
            state.executeUpdate(String.format(
                    "DELETE FROM extractionrules WHERE `domain`='%s'", domain));
            state.close();

        } catch (SQLException e) {
            //System.err.println("Error: " + e);
            return false;
        }
        return true;
    }
}
