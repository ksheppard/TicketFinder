/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.User;
import SQL.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kyran
 */
public class CreateAccount extends HttpServlet {

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

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordconf = request.getParameter("passwordconf");

        String errorMsg = "";
        UserDB userDB = new UserDB((Connection) request.getServletContext().getAttribute("connection"));

        if (userDB.checkExists(email)) {
            errorMsg = "There is already an account assigned to this email address.";
        } else if (password.length() > 16 || password.length() < 8 || Pattern.matches("\\p{Punct}", password)) { //may be able to do in javascript
            errorMsg = "Password must be 8-16 characters long and must only contains letters or numbers.";
        } else if (password.equals(passwordconf)) {
            errorMsg = "Passwords dont match.";
        }

        if (errorMsg.isEmpty()) {
            //if passed validation

            User user = userDB.addUser(email, password);
            if (user != null) {
                request.getServletContext().setAttribute("user", user);
                
                RequestDispatcher view = request.getRequestDispatcher("putsomethinghere");
                view.forward(request, response);
            }
            else{
                request.setAttribute("errorMsg", "Account creation failed");
                RequestDispatcher view = request.getRequestDispatcher("putsomethinghere");
                view.forward(request, response);
            }
        } else {
            request.setAttribute("errorMsg", errorMsg);
            RequestDispatcher view = request.getRequestDispatcher("putsomethinghere");
            view.forward(request, response);
        }


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
