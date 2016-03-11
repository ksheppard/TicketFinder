/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Structures;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author Kyran
 */
public class ErrorReport {
    private int id;
    private Timestamp date;
    private String domain;
    private String url;
    private String searchQuery;
    private boolean checked;

    public ErrorReport(int id, Timestamp date, String domain, String url, String searchQuery, boolean checked) {
        this.id = id;
        this.date = date;
        this.domain = domain;
        this.url = url;
        this.searchQuery = searchQuery;
        this.checked = checked;
    }

    public ErrorReport(Timestamp date, String domain, String url, String searchQuery) {
        this.date = date;
        this.domain = domain;
        this.url = url;
        this.searchQuery = searchQuery;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    
}
