<%-- 
    Document   : bbb
    Created on : Jan 8, 2015, 3:12:58 PM
    Author     : firstuser
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="javax.xml.transform.TransformerException"%>
<%@page import="com.relecotech.bbb.api.APIGenerator"%>
<%@page import="com.relecotech.bbb.api.XmlParser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%
             Map<String, String> responceMap = new HashMap<String, String>();
        
             
        %>
    <button onclick="<% try {
                // XmlParser parser=new XmlParser(new APIGenerator().createAPI("create", "name=Test+Meeting&meetingID=abc123&attendeePW=111222&moderatorPW=333444"));
               responceMap =  XmlParser.runAPI(new APIGenerator().createAPI("create", "name=Test+Meeting&meetingID=abc123&attendeePW=111222&moderatorPW=333444"));
            } catch (TransformerException ex) {
                Logger.getLogger(APIGenerator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(APIGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.print("The meeting status="+responceMap.get("returncode"));
            out.print("Your meeting id is="+responceMap.get("meetingID"));
    
    %> ">Create Meeting</button>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
