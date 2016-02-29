<%-- 
    Document   : testDataConfirmation
    Created on : 03-Feb-2016, 20:34:33
    Author     : Kyran
--%>

<%@page import="Models.FeatureEnum"%>
<%@page import="Models.SiteFeatures"%>
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
        
        <form action="addTestingData.jsp">
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
                List<SiteFeatures> trainingData = (List<SiteFeatures>) request.getServletContext().getAttribute("trainingIndData");
                for (int i = 0; i < trainingData.size(); i++) {
                    SiteFeatures site = trainingData.get(i);
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
    </body>
</html>
