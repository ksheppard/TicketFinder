/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Kyran
 */
public class User {
    private int ID;
    private String emailAddr;
    private String password; //should this be here? probably not because only should need it 
    private List<Address> addressList;
    private boolean isAdmin;
    private boolean mailList; //wont be used but store in DB

    public User(int ID, String emailAddr, String password, List<Address> addressList, boolean isAdmin) {
        this.ID = ID;
        this.emailAddr = emailAddr;
        this.password = password;
        this.addressList = addressList;
        this.isAdmin = isAdmin;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isMailList() {
        return mailList;
    }

    public void setMailList(boolean mailList) {
        this.mailList = mailList;
    }

    
    
    
}
