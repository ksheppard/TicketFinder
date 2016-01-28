/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author Kyran
 */
@WebListener()
public class DBListener implements ServletContextListener {

   Connection conn = null;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        ServletContext sc = sce.getServletContext();
        String db = sc.getInitParameter("Database");
        String user = sc.getInitParameter("User");
        String password = sc.getInitParameter("Password");
      //  String vendor = sc.getInitParameter("vendor");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db.trim(), user.trim(), password.trim()); 
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db.trim(), "root", "");
        }
        catch(ClassNotFoundException | SQLException e) {
            sc.setAttribute("error", e);
        }
       // DBBean dbBean = new DBBean();
       // dbBean.setTable(table);
       // sc.setAttribute("db", dbBean);
        sc.setAttribute("connection", conn);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       try { conn.close(); } catch(SQLException e) {}
    }
}
