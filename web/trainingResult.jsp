<%-- 
    Document   : trainingResult
    Created on : 11-Mar-2016, 12:46:38
    Author     : Kyran
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            boolean success = (boolean) request.getAttribute("success");
            
            if(success){
               
            %>
            <h1 color="green">Training Successful</h1><br><br>
            Make sure you test the newly created wrapper <a href="addTestData.jsp">here</a>
        <%
        }
            else{
        %>
            <h1 color="red">Training Unsuccessful</h1><br><br>
        
            Check the training data used and try again <a href="addTrainingData.jsp">here</a>
        
        <%
        }
        %>
    </body>
</html>
