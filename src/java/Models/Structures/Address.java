/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Structures;

/**
 *
 * @author Kyran
 */
public class Address {
    private String addr1;
    private String addr2;
    private String addr3;
    private String addr4;
    private String Postcode;
    private int id;

    public Address(String addr1, String addr2, String addr3, String addr4, String Postcode) {
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.addr3 = addr3;
        this.addr4 = addr4;
        this.Postcode = Postcode;
    }

    public Address(String addr1, String addr2, String addr3, String addr4, String Postcode, int id) {
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.addr3 = addr3;
        this.addr4 = addr4;
        this.Postcode = Postcode;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAddr3() {
        return addr3;
    }

    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    public String getAddr4() {
        return addr4;
    }

    public void setAddr4(String addr4) {
        this.addr4 = addr4;
    }

    public String getPostcode() {
        return Postcode;
    }

    public void setPostcode(String Postcode) {
        this.Postcode = Postcode;
    }
    
    
}
