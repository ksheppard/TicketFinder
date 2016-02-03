/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Kyran
 */
public class SiteValidator {
    
    public SiteValidator(){
        //used by the TestDataDB to check if test sites are still valid
        // http://stackoverflow.com/questions/10551813/check-if-url-is-valid-or-exists-in-java
    }
    
    public boolean isValid(String url){
        return true;
    }
    
    
}
