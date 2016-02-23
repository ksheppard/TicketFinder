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
        <title>TicketFinder</title>
    </head>
    <div data-role="page">
  <div data-role="header">
  <h1>TicketFinder</h1>
  </div>

  <div data-role="main" class="ui-content">
    <form method="post" action="CreateAccount.do">
      <fieldset data-role="collapsible">
        <legend>Create an account</legend>
          <label for="email">Email Address </label>
          <input type="text" name="email" id="email">
          <label for="password">Password </label>
          <input type="password" name="password" id="password">
          <label for="passwordconf">Confirm Password </label>
          <input type="password" name="passwordconf" id="passwordconf">
      <input type="submit" data-inline="true" value="Register">
      </fieldset>
    </form>
      
      <form method="post" action="Login.do">
      <fieldset data-role="collapsible">
        <legend>Login</legend>
          <label for="email">Email Address </label>
          <input type="text" name="email" id="email">
          <label for="password">Password </label>
          <input type="password" name="password" id="password">	
      <input type="submit" data-inline="true" value="Login">
      </fieldset>
    </form>
  </div>
</div>
</html>
