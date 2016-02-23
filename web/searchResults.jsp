<%-- 
    Document   : searchResults
    Created on : 23-Feb-2016, 17:07:41
    Author     : Kyran
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Page Title</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
        <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
        <script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
        <style>
            #page {
                margin-left: 200px;
            }
            #maincontent {
                float: right;
                width: 100%;
                background-color: #F0F0F0;
            }
            #leftcontent{
                float: left;
                width: 200px;
                margin-left: -200px;
                background-color: #CCCCCC;
                position:fixed
            }
            #sortingfilters{
                margin: 10px;
            }
            #clearingdiv {
                clear: both;
            }
        </style>
    </head>
    <body>

        <div id="page">
            <div id="maincontent">
                <div id="firstcontent">firstcontent</div>
                <div id="secondcontent">secondcontent</div>


            </div>
            <div id="leftcontent">
                <h5>Filter Your Results</h5><br>
                <div id="sortingfilters">
                    <form method="GET" action="sortResults.do">
                        <fieldset data-role="collapsible">
                            <legend>Sort by</legend>
                            <input type="radio" name="sort" value="Date"> Date<br>
                            <input type="radio" name="sort" value="Distance"> Distance<br>
                            <input type="radio" name="sort" value="A-Z"> A-Z<br>
                            <input type="radio" name="sort" value="Z-A"> Z-A<br>
                        </fieldset>
                    </form>
                </div>
                <div id="sortingfilters">
                    <form method="GET" action="filterResults.do">
                        <fieldset data-role="collapsible">
                            <legend>Date</legend>
                            <input type="date" name="dateStart" value=""> From <br>
                            <input type="date" name="dateEnd" value=""> Until <br>
                        </fieldset>
                    </form>
                </div>
                <div id="sortingfilters">
                    <form method="GET" action="filterResults.do">
                        <fieldset data-role="collapsible">
                            <legend>Distance</legend>
                            Within 
                            <select>
                                <option value="0">Any</option>
                                <option value="5">5</option>
                                <option value="10">10</option>
                                <option value="20">20</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                            </select> Miles of location<br><br>
                            Location: <input type="text" name="location" value="">
                        </fieldset>
                    </form>
                </div>
                <div id="sortingfilters">
                    <form method="GET" action="filterResults.do">
                        <fieldset data-role="collapsible">
                            <legend>Ticket provider</legend>
                            
                            <!-- need to have checkboxes for all displayed in alphbetical and a check/uncheck all button -->
                            
                        </fieldset>
                    </form>
                </div>
            </div>

        </div>
        <div id="clearingdiv"></div>
    </div>

</body>
</html>
