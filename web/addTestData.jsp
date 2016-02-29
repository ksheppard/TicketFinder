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
        <h1>Add test data from file or select existing data from database</h1>
        Select from database: <br>
        <form action='TestDataFromDB.do' method='POST'>
            Data type: <input type="radio" checked="true" name="type" value="0"> Individual
            <input type="radio" name="type" value="1"> List<br>
            Domain: <input type='text' name='domain'><br><br>
            <input type='submit' name='upload_btn' value='upload'>
        </form>
        <br><br><br><br><br><br>
        Add file: <br>
        <form action='AddTrainingData.do' method='POST' enctype="multipart/form-data">
            Data type: <input type="radio" name="type" value="0"> Individual
            <input type="radio" name="type" value="1"> List<br>
            <input type='file' name='file'><br><br>
            <input type="hidden" name="action" value="test">
            <input type='submit' name='upload_btn' value='upload'>
        </form>
    </body>
</html>
