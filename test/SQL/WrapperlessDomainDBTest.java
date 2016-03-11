/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.Structures.WrapperlessDomain;
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
public class WrapperlessDomainDBTest {
    
    java.sql.Connection conn;
    
    public WrapperlessDomainDBTest() {
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
     * Test of addDomain method, of class WrapperlessDomainDB.
     */
    @Test
    public void testAddDomain() {
        System.out.println("addDomain");
        WrapperlessDomain wd = null;
        WrapperlessDomainDB instance = new WrapperlessDomainDB(conn);
        boolean expResult = false;
        boolean result = instance.addDomain(wd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incrementHitCount method, of class WrapperlessDomainDB.
     */
    @Test
    public void testIncrementHitCount() {
        System.out.println("incrementHitCount");
        String domain = "";
        WrapperlessDomainDB instance = new WrapperlessDomainDB(conn);
        boolean expResult = false;
        boolean result = instance.incrementHitCount(domain);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeCheckedStatus method, of class WrapperlessDomainDB.
     */
    @Test
    public void testChangeCheckedStatus() {
        System.out.println("changeCheckedStatus");
        WrapperlessDomain wd = null;
        WrapperlessDomainDB instance = new WrapperlessDomainDB(conn);
        boolean expResult = false;
        boolean result = instance.changeCheckedStatus(wd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteDomain method, of class WrapperlessDomainDB.
     */
    @Test
    public void testDeleteDomain() {
        System.out.println("deleteDomain");
        WrapperlessDomain wd = null;
        WrapperlessDomainDB instance = new WrapperlessDomainDB(conn);
        boolean expResult = false;
        boolean result = instance.deleteDomain(wd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDomains method, of class WrapperlessDomainDB.
     */
    @Test
    public void testGetDomains() {
        System.out.println("getDomains");
        WrapperlessDomainDB instance = new WrapperlessDomainDB(conn);
        List<WrapperlessDomain> expResult = null;
        List<WrapperlessDomain> result = instance.getDomains();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
