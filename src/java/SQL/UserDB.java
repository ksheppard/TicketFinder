/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Models.Encryptor;
import Models.Structures.Address;
import Models.Structures.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kyran
 */
public class UserDB {

    private Connection conn;

    public UserDB(Connection conn) {
        this.conn = conn;
    }

    public User loginUser(String email, String pass) {
        //check account exists
        //check passowrd is valid after run through encryption
        //return null if cantg login and handle
        User user = null;

        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from users WHERE `Email` = '%s' and `Password` = '%s'", Encryptor.encrypt(email), Encryptor.mD5Encryption(pass)));
            while (rs.next()) {
                //domain is column 1
                int id = rs.getInt("Id");
                boolean isAdmin = rs.getBoolean("IsAdmin");

                //get addresses here
                user = new User(id, email, pass, getAddresses(id), isAdmin);
                break;
            }

            state.close();
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return user;
    }

    public User addUser(String email, String pass) {

        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("INSERT into users (`Email`, `Password`, `IsAdmin`) values ('%s', '%s', false)",  Encryptor.encrypt(email), Encryptor.mD5Encryption(pass)));

            state.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        //if adds successfully
        //will extract ID also
        return loginUser(email, pass);

        //else return null;
    }

    public boolean editUser(User user) {
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("UPDATE users SET Email = %s WHERE Id = %d", Encryptor.encrypt( user.getEmailAddr()), user.getID()));

            state.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        //if adds successfully
        //will extract ID also
        return true;
    }

    public boolean resetPassword(User user) {
        //changes password to randomly generated string that can email to their email address

        return false;
    }

    public boolean changePassword(User user, String newPass) {
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("UPDATE users SET Password = %s WHERE Id = %d", Encryptor.mD5Encryption(newPass), user.getID()));

            state.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return false;
        }

        //if adds successfully
        //will extract ID also
        return true;
    }

    public boolean checkExists(String email) {
        boolean exists = false;

        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from users WHERE `Email` = '%s'", Encryptor.encrypt(email)));
            while (rs.next()) {
                //domain is column 1
                exists = true;
                break;
            }

            state.close();
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return exists;
    }

    private List<Address> getAddresses(int id) {
        List<Address> addressList = new ArrayList<Address>();

        try {
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(String.format("SELECT * from userAddress WHERE `UserID` = '%d'", id));
            while (rs.next()) {
                //domain is column 1
                addressList.add(new Address(
                        rs.getString("Addr1"),
                        rs.getString("Addr2"),
                        rs.getString("Addr3"),
                        rs.getString("Addr4"),
                        rs.getString("Postcode")
                ));

                break;
            }

            state.close();
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return null;
        }

        return addressList;
    }

    public boolean addAddress(int id, Address addr) {
        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("INSERT into userAddress values (%d, '%s', '%s', '%s', '%s', '%s')", id, addr.getAddr1(), addr.getAddr2(), addr.getAddr3(), addr.getAddr4(), addr.getPostcode()));

            state.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return false;
        }

        //if adds successfully
        //will extract ID also
        return true;

    }

    public boolean editAddress(Address addr) {

        try {
            Statement state = conn.createStatement();
            state.executeUpdate(String.format("UPDATE userAddress SET Addr1 = %s, Addr2 = %s, Addr3 = %s, Addr4 = %s, Postcode = %s, WHERE AddrId = %d", addr.getAddr1(), addr.getAddr2(), addr.getAddr3(), addr.getAddr4(), addr.getId()));

            state.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return false;
        }

        //if adds successfully
        //will extract ID also
        return true;

    }

    public int getNumOfUsers() {
        int count = -1;

        try {
            Statement state = conn.createStatement();

            ResultSet rs = state.executeQuery("SELECT COUNT(*) AS rowcount FROM `users`;");
            while (rs.next()) {
                count = rs.getInt("rowcount");
            }
            state.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
            return -1;
        }

        return count;
    }

    
}
