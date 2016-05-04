<%-- 
    Document   : trainingDataListConfirmation
    Created on : 25-Feb-2016, 12:35:02
    Author     : Kyran
--%>

<%@page import="Models.Structures.TicketListFeatures"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Training Data</title>
    </head>
    <body>
        <jsp:include page="Menu.jsp" />
        <h1>Confirm Training Data</h1>
        
        <form action="addTrainingData.jsp">
            <input type="submit" value="Change file">
        </form>
        <br>
        <form action="train.do"  method='GET'>
            <input type="hidden" name="type" value="1">
            <input type="submit" value="Continue">
        </form>
        <br>
        <table  border="1" style="width:50%">
            <%
                List<TicketListFeatures> trainingData = (List<TicketListFeatures>) request.getServletContext().getAttribute("trainingListData");
                int maxLinks = -1;
                for(int i = 0; i < trainingData.size(); i++) {
                    if(maxLinks == -1 || maxLinks < trainingData.get(i).getUrlList().size()){
                         maxLinks = trainingData.get(i).getUrlList().size();
                    }
                }
                %>
            <tr>
                <th>Domain</th>
                <th>URL</th> 
                <th>numOfLinks</th> 
                <%
                    for(int i = 0; i < maxLinks; i++){
                        out.print("<th>Link " + (i + 1) +"</th> ");
                    }
                %>
            </tr>
            <%
                for (int i = 0; i < trainingData.size(); i++) {
                    TicketListFeatures ticketList = trainingData.get(i);
            %>
            <tr>
                <td> <%= ticketList.getDomain() %> </td>
                <td> <%= ticketList.getUrl() %> </td>
                <td> <%= ticketList.getUrlList().size() %> </td>
                <%
                    for(int j = 0; j < ticketList.getUrlList().size(); j++){
                        out.print("<td>" + ticketList.getUrlList().get(j) + "</td>");
                    }
                %>
            </tr>
            <%
                } //ending the loop from above
            %>
        </table>
    </body>
</html>
