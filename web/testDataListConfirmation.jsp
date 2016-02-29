<%-- 
    Document   : testDataListConfirmation
    Created on : 25-Feb-2016, 16:10:29
    Author     : Kyran
--%>

<%@page import="Models.TicketListFeatures"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Training Data</title>
    </head>
    <body>
        <h1>Confirm Training Data</h1>
        
        <form action="addTestData.jsp">
            <input type="submit" value="Change file">
        </form>
        <br>
        <form action="test.do"  method='GET'>
            <input type="hidden" name="type" value="1">
            <input type="submit" value="Continue">
        </form>
        <br>
        <table  border="1" style="width:50%">
            <tr>
                <th>Domain</th>
                <th>URL</th> 
                <th>numOfLinks</th> 
                <th>Link 1</th> 
                <th>Link 2</th> 
                <th>Link 3</th> 
                <th>Link 4</th> 
                <th>Link 5</th> 
            </tr>
            <%
                List<TicketListFeatures> trainingData = (List<TicketListFeatures>) request.getServletContext().getAttribute("trainingListData");
                for (int i = 0; i < trainingData.size(); i++) {
                    TicketListFeatures ticketList = trainingData.get(i);
            %>
            <tr>
                <td> <%= ticketList.getDomain() %> </td>
                <td> <%= ticketList.getUrl() %> </td>
                <td> <%= ticketList.getUrlList().size() %> </td>
                <td> <%= ticketList.getUrlList().size() > 0 ? ticketList.getUrlList().get(0) : "" %> </td>
                <td> <%= ticketList.getUrlList().size() > 1 ? ticketList.getUrlList().get(1) : "" %> </td>
                <td> <%= ticketList.getUrlList().size() > 2 ? ticketList.getUrlList().get(2) : "" %> </td>
                <td> <%= ticketList.getUrlList().size() > 3 ? ticketList.getUrlList().get(3) : "" %> </td>
                <td> <%= ticketList.getUrlList().size() > 4 ? ticketList.getUrlList().get(4) : "" %> </td>
            </tr>
            <%
                } //ending the loop from above
            %>
        </table>
    </body>
</html>
