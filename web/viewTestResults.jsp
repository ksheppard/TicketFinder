<%-- 
    Document   : viewTestResults
    Created on : 29-Jan-2016, 17:14:08
    Author     : Hannah
--%>

<%@page import="Models.FeatureEnum"%>
<%@page import="Models.TestResult"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Test Results</h1>
        
        <table  border="1" style="width:48%">
            <tr>
                <th>Domain</th>
                <th>URL</th> 
                <th># Correct</th> 
                <th># Total</th> 
            </tr>
            <%
                List<TestResult> testResults = (List<TestResult>) request.getServletContext().getAttribute("testResults");

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
