<%-- 
    Document   : testDataConfirmation
    Created on : 03-Feb-2016, 20:34:33
    Author     : Kyran
--%>

<%@page import="Models.Structures.TicketListFeatures"%>
<%@page import="Models.Enums.FeatureEnum"%>
<%@page import="Models.Structures.SiteFeatures"%>
<%@page import="java.util.List"%>
<%@page import="Models.WrapperHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Confirm Test Data</h1>
        
        <form action="addTestData.jsp">
            <input type="submit" value="Change file">
        </form>
        <br>
        <form action="test.do"  method='GET'>
            <input type="hidden" name="type" value="0">
            <input type="submit" value="Continue">
        </form>
        <br>
        <table  border="1" style="width:50%">
            <tr>
                <th>Domain</th>
                <th>URL</th> 
                <th>Date</th> 
                <th>Time</th> 
                <th>Artist</th> 
                <th>Price</th> 
                <th>Location</th> 
            </tr>
            <%
                List<SiteFeatures> indTestData = (List<SiteFeatures>) request.getServletContext().getAttribute("trainingIndData");
                for (int i = 0; i < indTestData.size(); i++) {
                    SiteFeatures site = indTestData.get(i);
            %>
            <tr>
                <td> <%= site.getDomain() %> </td>
                <td> <%= site.getUrl() %> </td>
                <td> <%= site.getFeatureMap().get(FeatureEnum.Date) %> </td>
                <td> <%= site.getFeatureMap().get(FeatureEnum.Time) %> </td>
                <td> <%= site.getFeatureMap().get(FeatureEnum.Artist) %> </td>
                <td> <%= site.getFeatureMap().get(FeatureEnum.Price) %> </td>
                <td> <%= site.getFeatureMap().get(FeatureEnum.Location) %> </td>
            </tr>
            <%
                } //ending the loop from above
            %>
        </table>
        <br>
        <table  border="1" style="width:50%">
            <%
                List<TicketListFeatures> listTestData = (List<TicketListFeatures>) request.getServletContext().getAttribute("trainingListData");
                int maxLinks = -1;
                for(int i = 0; i < listTestData.size(); i++) {
                    if(maxLinks == -1 || maxLinks < listTestData.get(i).getUrlList().size()){
                         maxLinks = listTestData.get(i).getUrlList().size();
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
                for (int i = 0; i < listTestData.size(); i++) {
                    TicketListFeatures ticketList = listTestData.get(i);
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
