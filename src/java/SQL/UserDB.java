/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.User;
import java.sql.Connection;

/**
 *
 * @author Kyran
 */
public class UserDB {
    private Connection conn;

    public UserDB(Connection conn) {
        this.conn = conn;
    }
    
    public User loginUser(String email, String pass){
        //check account exists
        //check passowrd is valid after run through encryption
        //return null if cantg login and handle
        
        return null;
    }
    
    public User addUser(String email, String pass){
        
        //if adds successfully
        //will extract ID also
        return loginUser(email, pass);
        
        //else return null;
    }
    
    public void editUser(){
        
    }
    
    public void resetPassword(){
        //changes password to randomly generated string that can email to their email address
    }
    
    public void changePassword(User user, String oldPass, String newPass){
        //make sure they have entered new passowrd twice
        
    }

    public boolean checkExists(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
