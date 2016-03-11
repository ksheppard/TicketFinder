/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.Structures.User;
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
public class UserDBTest {
    
    java.sql.Connection conn;
    
    public UserDBTest() {
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
     * Test of loginUser method, of class UserDB.
     */
    @Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String email = "";
        String pass = "";
        UserDB instance = new UserDB(conn);
        User expResult = null;
        User result = instance.loginUser(email, pass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addUser method, of class UserDB.
     */
    @Test
    public void testAddUser() {
        System.out.println("addUser");
        String email = "";
        String pass = "";
        UserDB instance = new UserDB(conn);
        User expResult = null;
        User result = instance.addUser(email, pass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editUser method, of class UserDB.
     */
    @Test
    public void testEditUser() {
        System.out.println("editUser");
        User user = null;
        UserDB instance = new UserDB(conn);
        boolean expResult = true;
        boolean result = instance.editUser(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetPassword method, of class UserDB.
     */
    @Test
    public void testResetPassword() {
        System.out.println("resetPassword");
        User user = null;
        UserDB instance = new UserDB(conn);
        boolean expResult = true;
        boolean result = instance.resetPassword(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changePassword method, of class UserDB.
     */
    @Test
    public void testChangePassword() {
        System.out.println("changePassword");
        User user = null;
        String newPass = "";
        UserDB instance = new UserDB(conn);
        boolean expResult = true;
        boolean result = instance.changePassword(user, newPass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkExists method, of class UserDB.
     */
    @Test
    public void testCheckExists() {
        System.out.println("checkExists");
        String email = "";
        UserDB instance = new UserDB(conn); 
        boolean expResult = true;
        boolean result = instance.checkExists(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
