<%-- 
    Document   : login
    Created on : 18-Feb-2016, 18:32:10
    Author     : Kyran
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
        <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
        <script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>

 <%--        <script type="text/javascript">
            function validateLogin() {

                var email = document.loginForm.email.value;
                if (email == null || email == "") {
                    alert("Error: Email must be entered!");
                    document.loginForm.email.focus();
                    return false;
                }

                var re = /\S+@\S+\.\S+/;
                if (!re.test(email)) {
                    alert("Error: Not a valid email address!");
                    document.loginForm.email.focus();
                    return false;
                }


                var password = document.loginForm.password.value;
                if (password == null || password == "") {
                    alert("Error: Password must be entered!");
                    document.loginForm.password.focus();
                    return false;
                }

                re = /^\w+$/;
                if (!re.test(pass)) {
                    alert("Error: Password must contain only letters, numbers and underscores!");
                    document.loginForm.username.focus();
                    return false;
                }
                return true;

            }

            function validateCreateAcc() {
                var email = document.createAccountForm.email.value;
                if (email == null || email == "") {
                    alert("Error: Email must be entered!");
                    document.createAccountForm.email.focus();
                    return false;
                }

                var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                if (re.test(email)) {
                    alert("Error: Not a valid email address!");
                    document.createAccountForm.email.focus();
                    return false;
                }

                var password = document.createAccountForm.password.value;
                if (password == null || password == "") {
                    alert("Error: Password must be entered!");
                    document.createAccountForm.password.focus();
                    return false;
                }

                re = /^\w+$/;
                if (!re.test(pass)) {
                    alert("Error: Password must contain only letters, numbers and underscores!");
                    form.password.focus();
                    return false;
                }

                var passwordconf = document.createAccountForm.passwordconf.value;

                if (password != passwordconf) {
                    alert("Error: Passwords don't match!");
                    document.createAccountForm.password.focus();
                }

                return true;
            }
        </script>
--%>
        <title>TicketFinder</title>
    </head>
    <body>
        
        <div data-role="page">
            <div data-role="header">
                <jsp:include page="Menu.jsp" />
            </div>

            <%
                Object attIsLogin = request.getAttribute("isLogin");
                Object attEmail = request.getAttribute("email");
                Object attPass = request.getAttribute("password");
                Object attPassconf = request.getAttribute("passwordconf");
                Object attError = request.getAttribute("errorMsg");

                boolean isLogin = attIsLogin == null || attIsLogin.toString().equals("") ? false : Boolean.parseBoolean(attIsLogin.toString());
                boolean isCreate = attIsLogin == null || attIsLogin.toString().equals("") ? false : !Boolean.parseBoolean(attIsLogin.toString());

                String email = attEmail == null ? "" : attEmail.toString();
                String password = attPass == null ? "" : attPass.toString();
                String passwordConf = attPassconf == null ? "" : attPassconf.toString();
                String errorMsg = attError == null ? "" : attError.toString();

                request.setAttribute("isLogin", "");
                request.setAttribute("email", "");
                request.setAttribute("password", "");
                request.setAttribute("passwordconf", "");
                request.setAttribute("errorMsg", "");
            %>

            <div data-role="main" class="ui-content">
                <form method="post" name="createAccountForm" onsubmit="return validateCreateAcc();"  action="CreateAccount.do">
                    <fieldset data-role="collapsible" data-collapsed="<%=!isCreate%>">
                        <legend>Create an account</legend>
                        <%
                            if(!isLogin && !errorMsg.equals("")){
                                out.println("<font color=\"red\">" + errorMsg + "</font>");
                            }
                        %>
                        <label for="email">Email Address </label>
                        <input type="text" name="email" id="email" value="<%=email%>">
                        <label for="password">Password </label>
                        <input type="password" name="password" id="password" value="<%=password%>">
                        <label for="passwordconf">Confirm Password </label>
                        <input type="password" name="passwordconf" id="passwordconf" value="<%=passwordConf%>">
                        <input type="submit" data-inline="true" value="Register">
                    </fieldset>
                </form>
                        
                <form method="post" name="loginForm" onsubmit="return validateLogin();" action="Login.do">
                    <fieldset data-role="collapsible" data-collapsed="<%=!isLogin%>">
                        <legend>Login</legend>
                        <%
                            if(isLogin && !errorMsg.equals("")){
                                out.println("<font color=\"red\">" + errorMsg + "</font>");
                            }
                        %>
                            <label for="email">Email Address </label>
                            <input type="text" name="email" id="email" value="<%=email%>">
                            <label for="password">Password </label>
                            <input type="password" name="password" id="password" value="<%=password%>">	
                            <input type="submit" data-inline="true" value="Login">
                    </fieldset>
                </form>
            </div>
        </div>
    </body>
</html>
