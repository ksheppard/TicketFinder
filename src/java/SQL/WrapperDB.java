/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.Enums.FeatureEnum;
import Models.Structures.Rule;
import Models.Structures.Wrapper;
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
    
    public Wrapper getWrapper(String domain, int type){
        List<Rule> ruleList = null;
        String domainVal = null;
        String head = null;
        String tail = null;
        int id = -1;
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from headtail WHERE domain = '%s' AND `type`= %b", domain, type));
            while (rs.next()) {
                id = rs.getInt("id");
                domainVal = rs.getString("domain").trim();
                head = SQLtoString(rs.getString("head").trim());
                tail = SQLtoString(rs.getString("tail").trim());
                ruleList = getRules(id);
            }
            
            state.close();
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        return new Wrapper(id, domainVal, head, tail, ruleList, type);
    }
    
    private List<Rule> getRules(int id){
        //return all rules from a specified domain
        //for use in testing system and for user queries
        List<Rule> ruleList = new ArrayList<Rule>();
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from extractionrules WHERE `id` = %d", id));
            while (rs.next()) {
                //domain is column 1
                FeatureEnum feature = FeatureEnum.valueOf(rs.getString("featureName").trim());
                String open = SQLtoString(rs.getString("open").trim());
                String close = SQLtoString(rs.getString("close").trim());
                String left = SQLtoString(rs.getString("leftVal").trim());
                String right = SQLtoString(rs.getString("rightVal").trim());
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
        int id = checkDomainExists(wrapper.getDomain(), wrapper.getType());
        if(id != -1) removeRules(id);
        
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("INSERT INTO headtail (`domain`, `head`, `tail`, `type`) VALUES ('%s','%s','%s',%d)"
                    ,wrapper.getDomain(), stringToSQL(wrapper.getHead()), stringToSQL(wrapper.getTail()), wrapper.getType()));
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
    
    private boolean addRules(String domain, List<Rule> ruleList){
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
    
    private int checkDomainExists(String domain, int type){
        int id = -1;
        
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from headtail WHERE `domain` = '%s' AND `type` = %d", domain, type));
            while (rs.next()) {
                id = rs.getInt("id");
            }
            
            state.close();
            rs.close();
            
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        return id;
    }
    
    private boolean removeRules(int id){
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format(
                    "DELETE FROM headtail WHERE `id`='%d'", id));
            state.executeUpdate(String.format(
                    "DELETE FROM extractionrules WHERE `id`='%d'", id));
            state.close();

        } catch (SQLException e) {
            //System.err.println("Error: " + e);
            return false;
        }
        return true;
    }
}
