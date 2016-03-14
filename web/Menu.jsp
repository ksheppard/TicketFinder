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
        <style>
            .button-container form,
            .button-container form div {
                display: inline;
                margin: 2px;
            }

            .button-container button {
                display: inline;
                vertical-align: middle;
            }

            .login-container form div {
                display: inline;
                horizontal-align: right;

            }

            .login-options form div {
                display: inline;
                horizontal-align: right;

            }
        </style>

    </head>
    <body>
        <%
            User user = request.getSession().getAttribute("user") != null ? (User)request.getSession().getAttribute("user") : null;
            %>
        <div class="button-container">
            <form action="homepage.jsp">
                <div>
                    <input type="submit" value="Home">
                </div>
            </form>
            <%
                if(user != null && user.isIsAdmin()){
            %>
            <form action="adminHomepage.jsp">
                <div>
                    <input type="submit" value="Admin Options">
                </div>
            </form>
            <%
                }
                if(user == null){
            %>
            <form name="loginForm" action="GoToLogin.do"  method="post">
                <div class="login-container">
                    <input type="hidden" name="isLogin" id="isLogin" value="true">
                    <input type="submit" value="Login">
                </div>
            </form>
            <form name="loginForm" action="GoToLogin.do"  method="post">
                <div class="login-container">
                    <input type="hidden" name="isLogin" id="isLogin" value="false">
                    <input type="submit" value="Create Account">
                </div>
            </form>
            <%
                }
                else{
            %>
            Logged in as: <%=user.getEmailAddr()%>
            <form action="userSettings.jsp">
                <div class="login-container">
                    <input type="submit" value="View user settings">
                </div>
            </form>
            <form name="loginForm" action="Logout.do"  method="post">
                <div class="login-container">
                    <input type="submit" value="Log out">
                </div>
            </form>
            
            <%
                }
            %>
        </div>
    </body>
</html>
