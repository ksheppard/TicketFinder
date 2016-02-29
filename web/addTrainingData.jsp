<%-- 
    Document   : addTrainingData
    Created on : 17-Jan-2016, 21:05:09
    Author     : Kyran
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add training data</title>
    </head>
    <body>
        <h1>Add training data!</h1>

        <form action='AddTrainingData.do' method='POST' enctype="multipart/form-data">
            Data type: <input type="radio" checked="true" name="type" value="0"> Individual
            <input type="radio"  name="type" value="1"> List<br>
            <input type='file' name='file'><br><br>
            <input type="hidden" name="action" value="train">
            <input type='submit' name='upload_btn' value='upload'>
        </form>
    </body>
</html>
