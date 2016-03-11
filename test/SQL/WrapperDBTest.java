/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.Structures.Wrapper;
import java.sql.DriverManager;
import java.sql.SQLException;
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
public class WrapperDBTest {
    
    java.sql.Connection conn;
    
    public WrapperDBTest() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/dissertationtest", "root", ""); 
        } catch (SQLException ex) {
            Logger.getLogger(UserDBTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WrapperDBTest.class.getName()).log(Level.SEVERE, null, ex);
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
     * Test of getWrapper method, of class WrapperDB.
     */
    @Test
    public void testGetWrapper() {
        System.out.println("getWrapper");
        String domain = "";
        int type = 0;
        WrapperDB instance  = new WrapperDB(conn);
        Wrapper expResult = null;
        Wrapper result = instance.getWrapper(domain, type);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addWrapper method, of class WrapperDB.
     */
    @Test
    public void testAddWrapper() {
        System.out.println("addWrapper");
        Wrapper wrapper = null;
        WrapperDB instance = new WrapperDB(conn);
        boolean expResult = false;
        boolean result = instance.addWrapper(wrapper);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
