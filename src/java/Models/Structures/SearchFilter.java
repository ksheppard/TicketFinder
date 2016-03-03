/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Structures;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Kyran
 */
public class SearchFilter {
    private String location;
    private int distance;
    private Date startDate;
    private Date endDate;
    private List<String> ticketProviders;

    public SearchFilter(String location, int distance, Date startDate, Date endDate, List<String> ticketProviders) {
        this.location = location;
        this.distance = distance;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ticketProviders = ticketProviders;
    }

    public SearchFilter() {
    }
    
    

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<String> getTicketProviders() {
        return ticketProviders;
    }

    public void setTicketProviders(List<String> ticketProviders) {
        this.ticketProviders = ticketProviders;
    }
    
    
    
  
}
