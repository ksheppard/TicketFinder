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
            ResultSet rs = state.executeQuery(String.format("SELECT * from wrapper WHERE `Domain` = '%s' AND `Type`= %d", domain, type));
            while (rs.next()) {
                id = rs.getInt("Id");
                domainVal = rs.getString("Domain").trim();
                head = SQLtoString(rs.getString("Head").trim());
                tail = SQLtoString(rs.getString("Tail").trim());
                ruleList = getRules(id);
            }
            
            state.close();
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        return new Wrapper(id, domainVal, head, tail, ruleList, type);
    }
    
    public List<Wrapper> getWrappers(String domain){
        List<Wrapper> wrapperList = new ArrayList<>();
        List<Rule> ruleList = null;
        String domainVal = null;
        String head = null;
        String tail = null;
        int type = -1;
        int id = -1;
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from wrapper WHERE `Domain` = '%s'", domain));
            while (rs.next()) {
                id = rs.getInt("Id");
                domainVal = rs.getString("Domain").trim();
                head = SQLtoString(rs.getString("Head").trim());
                tail = SQLtoString(rs.getString("Tail").trim());
                ruleList = getRules(id);
                type = rs.getInt("Type");
                wrapperList.add(new Wrapper(id, domainVal, head, tail, ruleList, type));
            }
            
            state.close();
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        return wrapperList;
    }
    
    private List<Rule> getRules(int id){
        //return all rules from a specified domain
        //for use in testing system and for user queries
        List<Rule> ruleList = new ArrayList<Rule>();
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from extractionrules WHERE `W_Id` = %d", id));
            while (rs.next()) {
                //domain is column 1
                FeatureEnum feature = FeatureEnum.valueOf(rs.getString("FeatureName").trim());
                String open = SQLtoString(rs.getString("Open").trim());
                String close = SQLtoString(rs.getString("Close").trim());
                String left = SQLtoString(rs.getString("LeftVal").trim());
                String right = SQLtoString(rs.getString("RightVal").trim());
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
            state.executeUpdate(String.format("INSERT INTO wrapper (`Domain`, `Head`, `Tail`, `Type`) VALUES ('%s','%s','%s',%d)"
                    ,wrapper.getDomain(), stringToSQL(wrapper.getHead()), stringToSQL(wrapper.getTail()), wrapper.getType()));
            state.close();

            
            id = checkDomainExists(wrapper.getDomain(), wrapper.getType());
        } catch (SQLException e) {
            return false;
        }
        
        
        
        return addRules(wrapper.getDomain(), wrapper.getRuleList(), id);
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
    
    private boolean addRules(String domain, List<Rule> ruleList, int id){
        //to be used for training the system
        
        try {
            Statement state = conn.createStatement();
            String updateString = "INSERT INTO extractionrules VALUES ";
            for (int i = 0; i < ruleList.size(); i++) {
                Rule rule = ruleList.get(i);
                updateString += String.format("('%s','%s','%s','%s','%s','%d')", rule.getFeatureName(), stringToSQL(rule.getOpen())
                        , stringToSQL(rule.getClose()), stringToSQL(rule.getLeft()), stringToSQL(rule.getRight()), id);
                if(i < ruleList.size() - 1) updateString += ",";
            }
            System.out.println(updateString);
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
            ResultSet rs = state.executeQuery(String.format("SELECT * from wrapper WHERE `domain` = '%s' AND `type` = %d", domain, type));
            while (rs.next()) {
                id = rs.getInt("id");
                break;
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
                    "DELETE FROM wrapper WHERE `Id`='%d'", id));
            state.executeUpdate(String.format(
                    "DELETE FROM extractionrules WHERE `W_Id`='%d'", id));
            state.close();

        } catch (SQLException e) {
            //System.err.println("Error: " + e);
            return false;
        }
        return true;
    }

    public List<Wrapper> getWrappersByType(int type, Object[] domains) {
        List<Wrapper> wrapperList = new ArrayList<>();
        Wrapper w;
        for (int i = 0; i < domains.length; i++) {
            w = getWrapper(domains[i].toString().trim(), type);
            if(w != null) wrapperList.add(w);
        }
        
        return wrapperList;
    }
}
