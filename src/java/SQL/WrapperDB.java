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
                head = SQLtoString(rs.getString(2).trim());
                tail = SQLtoString(rs.getString(3).trim());
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
                String open = SQLtoString(rs.getString(3).trim());
                String close = SQLtoString(rs.getString(4).trim());
                String left = SQLtoString(rs.getString(5).trim());
                String right = SQLtoString(rs.getString(6).trim());
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
            state.executeUpdate(String.format("INSERT INTO headtail VALUES ('%s','%s','%s')",wrapper.getDomain(), stringToSQL(wrapper.getHead()), stringToSQL(wrapper.getTail())));
            state.close();

        } catch (SQLException e) {
            return false;
        }
        return addRules(wrapper.getDomain(), wrapper.getRuleList());
    }
    
    private String stringToSQL(String string){
        //need to process anything putting into SQL first to make sure it is valid
        //need to escape any special characters
        String newString = "";
        
        for (int i = 0; i < string.length(); i++) {
            if(string.charAt(i) == '\"'
                    || string.charAt(i) == '\''){
                //add a second to escape
                newString += string.charAt(i);
            }
            newString += string.charAt(i);
        }
        
        return newString;
    }
    
    private String SQLtoString(String string){
        String newString = "";
        
        for (int i = 0; i < string.length(); i++) {
            newString += string.charAt(i);
            
            if(string.charAt(i) == '\"'
                    || string.charAt(i) == '\''){
                i++; //this skips past second occurance
            }
            
        }
        
        return newString;
    }
    
    public boolean addRules(String domain, List<Rule> ruleList){
        //to be used for training the system
        
        try {
            Statement state = conn.createStatement();
            String updateString = "INSERT INTO extractionrules VALUES ";
            for (int i = 0; i < ruleList.size(); i++) {
                Rule rule = ruleList.get(i);
                updateString += String.format("('%s','%s','%s','%s','%s','%s')", domain, rule.getFeatureName(), stringToSQL(rule.getOpen())
                        , stringToSQL(rule.getClose()), stringToSQL(rule.getLeft()), stringToSQL(rule.getRight()));
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
