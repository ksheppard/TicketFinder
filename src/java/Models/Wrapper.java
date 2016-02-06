/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kyran
 */
public class Wrapper {
    private String domain;
    private String head;
    private String tail;
    private List<Rule> ruleList;

    public Wrapper(String domain, String head, String tail, List<Rule> ruleList) {
        this.domain = domain;
        this.head = head;
        this.tail = tail;
        this.ruleList = ruleList;
    }
    
    public Wrapper(String domain) {
        this.domain = domain;
        this.ruleList = new ArrayList<Rule>(); //init rulelist so can be added to
    }
    
    public void addRule(Rule rule){
        if(ruleList == null)ruleList = new ArrayList<Rule>(); //shouldn't be null be check to be safe
        ruleList.add(rule);
    }
    
    public Rule getRule(int index){
        return ruleList.get(index);
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }
    
    
}
