<%-- 
    Document   : Menu
    Created on : 18-Feb-2016, 19:43:48
    Author     : Kyran
--%>

<%@page import="Models.Structures.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Page Title</title>
       
        <link rel="stylesheet" href="menuCSS.css">
    </head>
    <body>

        <%
           
            User user = request.getSession().getAttribute("user") != null ? (User) request.getSession().getAttribute("user") : null;
        %>
        
        <div class="navigation1">
            <ul class="menuLeft">
                <li><a href="homepage.jsp">Home</a></li>
                    <%
                        if (user != null && user.isIsAdmin()) {
                    %>
                <li><a href="adminHomepage.jsp">Admin Home</a></li>
                    <%
                        }
                    %>
                <li><a href="about.html">About</a></li>
            </ul>
            <ul class="menuRight">
                    <%
                        if (user == null) {
                    %>
                <li><form name="loginForm" action="GoToLogin.do"  method="post">
                        <input type="hidden" name="isLogin" id="isLogin" value="true">
                        <input type="submit" value="Login">
                    </form></li>
                <li><form name="loginForm" action="GoToLogin.do"  method="post">
                        <input type="hidden" name="isLogin" id="isLogin" value="false">
                        <input type="submit" value="Create Account">
                    </form></li>
                    <%
                    } else {
                    %>
                <li>Logged in as: <%=user.getEmailAddr()%></li>
                <li><form action="userSettings.jsp">
                        <input type="submit" value="View user settings">
                    </form></li>
                <li><form name="loginForm" action="Logout.do"  method="post">
                        <input type="submit" value="Log out">
                    </form></li>
                    <%
                        }
                    %>
            </ul>
        </div>
    </body>
</html>
