/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.FileReader;
import Models.Structures.SiteFeatures;
import Models.Structures.TicketListFeatures;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Kyran
 */
@WebServlet("/upload")
@MultipartConfig
public class AddTrainingData extends HttpServlet {

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

        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        String fileName = filePart.getSubmittedFileName();
        InputStream fileContent = filePart.getInputStream();

        FileReader fr = new FileReader();
        
        String action = request.getParameter("action");

        if (action.equals("train")) {
            int type = Integer.parseInt(request.getParameter("type"));
            
            if (type == 0) {
                List<SiteFeatures> list = fr.readIndDataFromFile(fileContent);
                request.getServletContext().setAttribute("trainingIndData", list);
                RequestDispatcher view = request.getRequestDispatcher("trainingDataConfirmation.jsp");
                view.forward(request, response);
            } else {
                List<TicketListFeatures> list = fr.readListDataFromFile(fileContent);
                request.getServletContext().setAttribute("trainingListData", list);
                RequestDispatcher view = request.getRequestDispatcher("trainingDataListConfirmation.jsp");
                view.forward(request, response);
            }
        }
        else{
            Part listFilePart = request.getPart("listFile"); // Retrieves <input type="file" name="file">
            String listFileName = listFilePart.getSubmittedFileName();
            InputStream listFileContent = listFilePart.getInputStream();
            
            List<SiteFeatures> siteList = fr.readIndDataFromFile(fileContent);
            request.getServletContext().setAttribute("trainingIndData", siteList);
            
            List<TicketListFeatures> linkList = fr.readListDataFromFile(listFileContent);
            request.getServletContext().setAttribute("trainingListData", linkList);
            
            RequestDispatcher view = request.getRequestDispatcher("testDataConfirmation.jsp");
            view.forward(request, response);
            
        }
        
//        if (type == 0) {
//            //if individual pages
//            List<SiteFeatures> list = fr.readIndDataFromFile(fileContent);
//            request.getServletContext().setAttribute("trainingIndData", list);
//
//            if (action.equals("train")) {
//                RequestDispatcher view = request.getRequestDispatcher("trainingDataConfirmation.jsp");
//                view.forward(request, response);
//            } else {
//                RequestDispatcher view = request.getRequestDispatcher("testDataConfirmation.jsp");
//                view.forward(request, response);
//            }
//        } else {
//            //if list of pages
//
//            List<TicketListFeatures> list = fr.readListDataFromFile(fileContent);
//            request.getServletContext().setAttribute("trainingListData", list);
//
//            String action = request.getParameter("action");
//            if (action.equals("train")) {
//                RequestDispatcher view = request.getRequestDispatcher("trainingDataListConfirmation.jsp");
//                view.forward(request, response);
//            } else {
//                RequestDispatcher view = request.getRequestDispatcher("testDataListConfirmation.jsp");
//                view.forward(request, response);
//            }
//
//        }

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
