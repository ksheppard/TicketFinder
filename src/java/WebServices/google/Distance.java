/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices.google;

/**
 *
 * @author Jarrett
 */
public class Distance {
    private String distanceAsString;
    private int distanceAsInt;
    
    public Distance(String distanceAsString, int distanceAsInt) {
        this.distanceAsInt = distanceAsInt;
        this.distanceAsString = distanceAsString;
    }
    
    public String getDistanceAsString() {
        return this.distanceAsString;
    }
    
    public int getDistanceAsInt() {
        return this.distanceAsInt;
    }
}
