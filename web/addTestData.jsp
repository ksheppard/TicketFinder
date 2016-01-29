<%-- 
    Document   : addTestData
    Created on : 29-Jan-2016, 17:13:56
    Author     : Hannah
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Test Data</title>
    </head>
    <body>
        <h1>Add Test Data</h1>
        <form action='test.do' method='POST' enctype="multipart/form-data">
            <input type='file' name='file'><br><br>
            <input type='submit' name='upload_btn' value='upload'>
        </form>
    </body>
</html>
