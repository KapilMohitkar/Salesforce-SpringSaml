<%-- 
    Document   : student
    Created on : Jan 22, 2015, 6:10:14 PM
    Author     : ruser2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!--    <center>  <h1>Wait! Presenter has not joined meeting yet!</h1>
             <h2>
                    Please check meeting scheduled time or try after few minutes.
                </h2>
             <a href="https://dev-tms-test-dev-ed.my.salesforce.com/">Back </a></center>-->
        <center> <img src="${pageContext.request.contextPath}/images/bbbtime.jpg" style="width:250px"/> </center>
        ${student}
    </body>
</html>