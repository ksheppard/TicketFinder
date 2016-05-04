<%-- 
    Document   : adminHomepage
    Created on : 29-Jan-2016, 13:56:26
    Author     : Hannah
--%>

<%@page import="Models.Structures.ErrorReport"%>
<%@page import="Models.Structures.WrapperlessDomain"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="SQL.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Admin Home</title>
        <link rel="stylesheet" href="menuCSS.css">
    </head>
    <body>
        <jsp:include page="Menu.jsp" />
        <%
            Connection conn = (Connection) request.getServletContext().getAttribute("connection");
            UserDB userDB = new UserDB(conn);
            ErrorReportDB errorReportDB = new ErrorReportDB(conn);
            WrapperlessDomainDB wrapperlessDB = new WrapperlessDomainDB(conn);

            List<WrapperlessDomain> wrapperlessList = wrapperlessDB.getDomains();
            int userCount = userDB.getNumOfUsers();
            List<ErrorReport> errorReportList = errorReportDB.getErrorReports();
        %>

        <div class="wrapper">
            <ul class="sideMenu">
                <li><a href="addTrainingData.jsp">Add Training data</a></li>
                <li><a href="addTestData.jsp">Test System</a></li>
            </ul>
            <div class="centralise">
                <div class="leftCol">
                    <div class="contentBox">
                        User count: <%=userCount%>
                    </div>
                    <div class="contentBox">
                        <table  border="1" title="Domains without wrappers" style="width:100%">
                            <caption>Domains without wrappers</caption>
                            <tr>
                                <th>Domain</th>
                                <th>Hit count</th>
                                    <%--               <th></th>--%>
                            </tr>
                            <%
                                for (int i = 0; i < wrapperlessList.size(); i++) {
                                    WrapperlessDomain wd = wrapperlessList.get(i);
                            %>
                            <tr>
                                <td><%=wd.getDomain()%></td>
                                <td><%=wd.getHitCount()%></td>
                                <%--              <td><%=wd.isChecked()%></td> --%>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                    </div>
                </div>
                <div class="rightCol">
                    
                    <div class="contentBox">
                        <table border="1" title="Error reports" style="width:100%">
                            <tr>
                            <caption>Error reports</caption>
                            <th>Date</th>
                            <th>Domain</th>
                            <th>Url</th>
                            <th>Search Query</th>
                                <%--             <th></th>--%>
                            </tr>
                            <%
                                for (int i = 0; i < errorReportList.size(); i++) {
                                    ErrorReport er = errorReportList.get(i);
                            %>
                            <tr>
                                <td><%=er.getDate().toString()%></td>
                                <td><%=er.getDomain()%></td>
                                <td><%=er.getUrl()%></td>
                                <td><%=er.getSearchQuery()%></td>
                                <%--             <td><%=er.isChecked()%></td>--%>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                    </div>
                </div>
            </div>
        </div>





    </body>
</html>
