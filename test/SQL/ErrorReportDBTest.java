/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.Structures.ErrorReport;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kyran
 */
public class ErrorReportDBTest {
    
    java.sql.Connection conn;
    
    public ErrorReportDBTest() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/dissertationtest", "root", ""); 
        } catch (SQLException ex) {
            Logger.getLogger(UserDBTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDBTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addErrorReport method, of class ErrorReportDB.
     */
    @Test
    public void testAddErrorReport() {
        System.out.println("addErrorReport");
        java.util.Date date= new java.util.Date();
        ErrorReport errorReport = new ErrorReport(new Timestamp(date.getTime()), "testDomain", "testURL.com/test/test", "an example search");
        ErrorReportDB instance = new ErrorReportDB(conn);
        boolean expResult = true;
        boolean result = instance.addErrorReport(errorReport);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getErrorReports method, of class ErrorReportDB.
     */
    @Test
    public void testGetErrorReports() {
        System.out.println("getErrorReports");
        ErrorReportDB instance  = new ErrorReportDB(conn);
        List<ErrorReport> expResult = null;
        List<ErrorReport> result = instance.getErrorReports();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeCheckStatus method, of class ErrorReportDB.
     */
    @Test
    public void testChangeCheckStatus() {
        System.out.println("changeCheckStatus");
        ErrorReport errorReport = null;
        ErrorReportDB instance = new ErrorReportDB(conn);
        boolean expResult = false;
        boolean result = instance.changeCheckStatus(errorReport);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteErrorReport method, of class ErrorReportDB.
     */
    @Test
    public void testDeleteErrorReport() {
        System.out.println("deleteErrorReport");
        ErrorReport errorReport = null;
        ErrorReportDB instance  = new ErrorReportDB(conn);
        boolean expResult = false;
        boolean result = instance.deleteErrorReport(errorReport);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
