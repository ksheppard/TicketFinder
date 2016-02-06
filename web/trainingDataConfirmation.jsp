<%-- 
    Document   : trainingDataConfirmation
    Created on : 02-Feb-2016, 23:48:40
    Author     : Kyran
--%>

<%@page import="Models.FeatureEnum"%>
<%@page import="Models.SiteFeatures"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="Models.WrapperHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Training Data</title>
    </head>
    <body>
        <h1>Confirm Training Data</h1>
        
        <form action="addTrainingData.jsp">
            <input type="submit" value="Change file">
        </form>
        <br>
        <form action="train.do"  method='GET'>
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
                WrapperHelper wrapperHelper = (WrapperHelper) request.getServletContext().getAttribute("trainingWrapperHelper");
                List<SiteFeatures> trainingData = wrapperHelper.getFile();
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
