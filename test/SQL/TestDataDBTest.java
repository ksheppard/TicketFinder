/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.Structures.SiteFeatures;
import Models.Structures.TicketListFeatures;
import java.sql.DriverManager;
import java.sql.SQLException;
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
public class TestDataDBTest {
    
    java.sql.Connection conn;
    
    public TestDataDBTest() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/dissertationtest", "root", ""); 
        } catch (SQLException ex) {
            Logger.getLogger(UserDBTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestDataDBTest.class.getName()).log(Level.SEVERE, null, ex);
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
     * Test of addIndTestData method, of class TestDataDB.
     */
    @Test
    public void testAddIndTestData() {
        System.out.println("addIndTestData");
        List<SiteFeatures> dataList = null;
        TestDataDB instance = new TestDataDB(conn);
        boolean expResult = false;
        boolean result = instance.addIndTestData(dataList);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndTestData method, of class TestDataDB.
     */
    @Test
    public void testGetIndTestData() {
        System.out.println("getIndTestData");
        String domain = "";
        TestDataDB instance = new TestDataDB(conn);
        List<SiteFeatures> expResult = null;
        List<SiteFeatures> result = instance.getIndTestData(domain);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addListTestData method, of class TestDataDB.
     */
    @Test
    public void testAddListTestData() {
        System.out.println("addListTestData");
        List<TicketListFeatures> dataList = null;
        TestDataDB instance = new TestDataDB(conn);
        boolean expResult = false;
        boolean result = instance.addListTestData(dataList);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getListTestData method, of class TestDataDB.
     */
    @Test
    public void testGetListTestData() {
        System.out.println("getListTestData");
        String domain = "";
        TestDataDB instance = new TestDataDB(conn);
        List<TicketListFeatures> expResult = null;
        List<TicketListFeatures> result = instance.getListTestData(domain);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
