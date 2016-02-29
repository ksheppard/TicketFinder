/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Enums.FeatureEnum;
import Models.Rule;
import Models.WrapperHelper;
import SQL.WrapperDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kyran
 */
public class Tests extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        testWrapperHelper();
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* This is to be used for any testing required aka sql testing */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Tests</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Tests at " + request.getContextPath() + "</h1>");
             //out.println(testSQL(request));
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private void testWrapperHelper(){
        WrapperHelper wh = new WrapperHelper();
       // wh.testMethods();
    }
    
    private String testSQL(HttpServletRequest request){
        WrapperDB wrapperDB = new WrapperDB((Connection) request.getServletContext().getAttribute("connection"));
        
        String result = "";
        try{
            List<Rule> ruleList = new ArrayList<Rule>();
            ruleList.add(new Rule(FeatureEnum.Date, "date head", "date left", "date right", "THIS IS NEW NOW"));
            ruleList.add(new Rule(FeatureEnum.Artist, "Artist head", "Artist left", "date right", "date tail"));
            ruleList.add(new Rule(FeatureEnum.Location, "Location head", "Location left", "date right", "date tail"));
            ruleList.add(new Rule(FeatureEnum.Price, "Price head", "Price left", "date right", "date tail"));
            ruleList.add(new Rule(FeatureEnum.Postcode, "", "", "", ""));
            
//            result += wrapperDB.addRules("testDomain", ruleList) + ",";
//            
//            ruleList = wrapperDB.getRules("doesntExist");
//            result += (ruleList.size() == 0) + ",";
//            
//            ruleList = wrapperDB.getRules("testDomain");
            result += (ruleList.size() != 0) + ",";
            for (int i = 0; i < ruleList.size(); i++) {
                result += ruleList.get(i).toString() + ",";
            }
        }
        catch(Exception e){
            result += ", thrown exception";
        }
        
        return result;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
