<%-- 
    Document   : Menu
    Created on : 18-Feb-2016, 19:43:48
    Author     : Kyran
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Page Title</title>
        <style>
            .button-container form,
            .button-container form div {
                display: inline;

            }

            .button-container button {
                display: inline;
                vertical-align: middle;
            }

            .login-container form div {
                display: inline;
                horizontal-align: right;

            }
        </style>

        <script>
            function validateLogin() {
                var x = document.forms["loginForm"]["email"].value;
                if (x == null || x == "") {
                    alert("Email must be entered");
                    return false;
                }
                var y = document.forms["loginForm"]["password"].value;
                if (y == null || y == "") {
                    alert("Password must be entered");
                    return false;
                }
            }
        </script>
    </head>
    <body>
        <div class="button-container">
            <form action="index.jsp">
                <div>
                    <input type="submit" value="Home">
                </div>
            </form>
            <form name="loginForm" action="Login.do" onsubmit="return validateLogin()" method="post">
                <div class="login-container">
                    <label for="email">Email Address </label>
                    <input type="text" name="email" id="email">
                    <label for="password">Password </label>
                    <input type="password" name="password" id="password">
                    <input type="submit" value="Login">
                </div>
            </form>

            <form action="index.jsp">
                <div class="login-container">
                    <input type="submit" value="Create Account">
                </div>
            </form>
        </div>

    </body>
</html>
