/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Structures;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kyran
 */
public class SearchResult {
    private Date date;
    private Time time;
    private String artist;
    private String location;
    private List<SiteFeatures> siteFeatures;

    private double distance;
    private String distanceLocation; //this will be set so know whether need to recalculate distance or already set
    
    public SearchResult(Date date, Time time, String artist, String location, List<SiteFeatures> siteFeatures) {
        this.date = date;
        this.time = time;
        this.artist = artist;
        this.location = location;
        this.siteFeatures = siteFeatures;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<SiteFeatures> getSiteFeatures() {
        return siteFeatures;
    }

    public void setSiteFeatures(List<SiteFeatures> siteFeatures) {
        this.siteFeatures = siteFeatures;
    }

    public double getDistance() {
        return distance;
    }

    private void setDistance(String location) {
        //needs to work out the distance from the location here
        
        this.distance = distance;
    }

    public String getDistanceLocation() {
        return distanceLocation;
    }

    public void setDistanceLocation(String distanceLocation) {
        if(!this.distanceLocation.equals(distanceLocation)){
            this.distanceLocation = distanceLocation;
            setDistance(distanceLocation);
        }
        
    }
    
    public void clearSites(){
        this.siteFeatures.clear();
    }
    
    public void addSite(SiteFeatures sf){
        this.siteFeatures.add(sf);
    }
    
    
    
    
}
