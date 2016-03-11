/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.Structures.ErrorReport;
import Models.Structures.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kyran
 */
public class ErrorReportDB {
    private Connection conn;

    public ErrorReportDB(Connection conn) {
        this.conn = conn;
    }
    
    public boolean addErrorReport(ErrorReport errorReport){
        
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("INSERT into errorReports values ('%s', '%s', '%s', '%s', %b)", 
                    errorReport.getDate(), errorReport.getDomain(), errorReport.getUrl(), errorReport.getSearchQuery(), errorReport.isChecked()));

            state.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return false;
        }
        
        return true;
    }
    
    public List<ErrorReport> getErrorReports(){
        List<ErrorReport> errorReportList = new ArrayList();
        
        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from ErrorReports"));
            while (rs.next()) {

                int id = rs.getInt("Id");
                String url = rs.getString("Url");
                String domain = rs.getString("Domain");
                String searchQuery = rs.getString("SearchQuery");
                Timestamp date = rs.getTimestamp("Date");
                boolean checked = rs.getBoolean("Checked");

                errorReportList.add(new ErrorReport(id, date, domain, url, searchQuery, checked));                
            }

            state.close();
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return null;
        }
        
        return errorReportList;
    }
    
    public boolean changeCheckStatus(ErrorReport errorReport){
         try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("UPDATE errorReports SET Checked = %b WHERE Id = %d"
                    , !errorReport.isChecked(), errorReport.getId()));

            state.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return false;
        }
        
        return true;
    }
    
    public boolean deleteErrorReport(ErrorReport errorReport){
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("DELETE FROM errorReports WHERE Id = %d"
                    , errorReport.getId()));

            state.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return false;
        }
        
        return true;
    }
}
