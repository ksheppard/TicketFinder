<%-- 
    Document   : viewTestResults
    Created on : 29-Jan-2016, 17:14:08
    Author     : Hannah
--%>

<%@page import="Models.Enums.FeatureEnum"%>
<%@page import="Models.Structures.TestResult"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="Menu.jsp" />
        <h1>Test Results</h1>
        <br><br>
        <h4>Individual ticket page results</h4>
        <table  border="1" style="width:48%">
            <tr>
                <th>Domain</th>
                <th>URL</th> 
                <th># Correct</th> 
                <th># Total</th> 
            </tr>
            <%
                List<TestResult> testResults = (List<TestResult>) request.getServletContext().getAttribute("indTestResults");

                for (int i = 0; i < testResults.size(); i++) {
                    TestResult tr = testResults.get(i);
            %>
            <tr>
                <td> <%= tr.getDomain() %> </td>
                <td> <%= tr.getUrl() %> </td>
                <%
                    if(tr.getNumFeaturesCorrect() == tr.getTotalFeatures()){
                        out.print("<td bgcolor=\"green\">" + tr.getNumFeaturesCorrect() + "</td>");
                    }
                    else out.print("<td bgcolor=\"red\">" + tr.getNumFeaturesCorrect() + "</td>");
                %>
                <td> <%= tr.getTotalFeatures()%> </td>
            </tr>
            <%
                } //ending the loop from above
            %>
        </table>
        
        <h4>List ticket page results</h4>
        <table  border="1" style="width:48%">
            <tr>
                <th>Domain</th>
                <th>URL</th> 
                <th># Correct</th> 
                <th># Total</th> 
            </tr>
            <%
                List<TestResult> listTestResults = (List<TestResult>) request.getServletContext().getAttribute("listTestResults");

                for (int i = 0; i < testResults.size(); i++) {
                    TestResult tr = testResults.get(i);
            %>
            <tr>
                <td> <%= tr.getDomain() %> </td>
                <td> <%= tr.getUrl() %> </td>
                <%
                    if(tr.getNumFeaturesCorrect() == tr.getTotalFeatures()){
                        out.print("<td bgcolor=\"green\">" + tr.getNumFeaturesCorrect() + "</td>");
                    }
                    else out.print("<td bgcolor=\"red\">" + tr.getNumFeaturesCorrect() + "</td>");
                %>
                <td> <%= tr.getTotalFeatures()%> </td>
            </tr>
            <%
                } //ending the loop from above
            %>
        </table>
        
    </body>
</html>
