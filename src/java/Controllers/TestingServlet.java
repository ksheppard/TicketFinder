/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Structures.SiteFeatures;
import Models.Structures.TestResult;
import Models.Structures.TicketListFeatures;
import Models.WrapperTester;
import SQL.TestDataDB;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kyran
 */
public class TestingServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<SiteFeatures> indTestData = (List<SiteFeatures>) request.getServletContext().getAttribute("trainingData");
        List<TestResult> indResults = new ArrayList<>();
        List<TicketListFeatures> listTestData = (List<TicketListFeatures>) request.getServletContext().getAttribute("listTrainingData");
        List<TestResult> listResults = new ArrayList<>();
        
        WrapperTester wt = new WrapperTester((Connection) request.getServletContext().getAttribute("connection"));
        
        if(indTestData.size() > 0){
            indResults = wt.performIndTests(indTestData);
        }
        if(listResults.size() > 0){
            listResults = wt.performListTests(listTestData);
        }
       
        request.getServletContext().setAttribute("indTestResults", indResults);
        request.getServletContext().setAttribute("listTestResults", listResults);
        
        
        RequestDispatcher view = request.getRequestDispatcher("viewTestResults.jsp");
        view.forward(request, response);

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
