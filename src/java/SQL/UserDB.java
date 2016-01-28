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
    
    public void addUser(){
        
    }
    
    public void editUser(){
        
    }
    
    public void resetPassword(){
        //changes password to randomly generated string that can email to their email address
    }
    
    public void changePassword(User user, String oldPass, String newPass){
        //make sure they have entered new passowrd twice
        
    }
}
