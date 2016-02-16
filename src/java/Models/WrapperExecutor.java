/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import SQL.WrapperDB;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kyran
 */
public class WrapperExecutor {
    public final static String MODIFIER_STRING = "|#|"; //unique string combinatioin that signifies that ignore the next bit of the string
    public final static String MODIFIER_REGEX = "[|#|]";
    
    WrapperDB wrapperDB;
    
    public WrapperExecutor(Connection conn) {
        wrapperDB = new WrapperDB(conn);
    }
    
    // <editor-fold desc="Extracting data from wrapper ">
    
    public String getValFromRule(String html, Rule rule) {
        //use this when testing and executing but run the head and tail through first so only passing substring of the html
        int indexStart = -1;
        int indexEnd = -1;

        if (!rule.getLeft().contains("|#|")) {
            indexStart = html.indexOf(rule.getLeft()) + rule.getLeft().length();
        } else {
            String[] split = rule.getLeft().split(MODIFIER_REGEX);
            int pointer = 0;
            while (true) {
                pointer = html.indexOf(split[0], pointer);
                boolean location = true;
                int innerPointer = pointer;
                //could do differently to use the max length of an unknown entity eg 30 from method where set the |#| IMPORTANT IF DOESNT WORK
                for (int j = 1; j < split.length; j++) {
                    innerPointer = html.indexOf(split[0], innerPointer);
                    if (innerPointer == -1) {
                        location = false;
                        break;
                    }
                }
                if (location) {
                    //have found it and can set the end index
                    indexStart = innerPointer + split[split.length - 1].length();
                    break;
                }
            }
        }
        //doesnt need to be nested as is repeated in both
        if (!rule.getRight().contains(MODIFIER_STRING)) {
            indexEnd = html.indexOf(rule.getRight(), indexStart) - 1;
        } else {
            String[] split = rule.getRight().split(MODIFIER_REGEX);
            int pointer = indexStart; //start here as know it cant be before and dont know the length of the item you are searching for
            while (true) { //should only go through once on this but have included in a loop just incase
                pointer = html.indexOf(split[0], pointer);
                boolean location = true;
                int innerPointer = pointer;
                //could do differently to use the max length of an unknown entity eg 30 from method where set the |#| IMPORTANT IF DOESNT WORK
                for (int j = 1; j < split.length; j++) {
                    innerPointer = html.indexOf(split[0], innerPointer);
                    if (innerPointer == -1) {
                        location = false;
                        break;
                    }
                }
                if (location) {
                    //have found it and can set the end index
                    indexStart = innerPointer + split[split.length - 1].length();
                    break;
                }
            }
        }
        return indexStart == -1 || indexEnd == -1 ? "" : html.substring(indexStart, indexEnd);
    }
    
    // </editor-fold>
    
    
}
