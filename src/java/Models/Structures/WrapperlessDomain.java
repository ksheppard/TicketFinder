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
public class WrapperlessDomain {
    private int id;
    private String domain;
    private boolean checked;
    private int hitCount;

    public WrapperlessDomain(int id, String domain, boolean checked, int hitCount) {
        this.id = id;
        this.domain = domain;
        this.checked = checked;
        this.hitCount = hitCount;
    }

    public WrapperlessDomain(String domain) {
        this.domain = domain;
        this.hitCount = 1;
        this.checked = false;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }
    
    
}
