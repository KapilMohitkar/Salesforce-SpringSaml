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
            Map<String, String> responceMeetingMap = new HashMap<String, String>();
             Map<String, String> responceJoinngMap = new HashMap<String, String>();


        %>
    <button onclick="<% try {
          //create meeting
           // responceMap = XmlParser.runAPI(new APIGenerator().createAPI("create", "name=Test+Meeting&meetingID=abc123&attendeePW=111222&moderatorPW=333444"));
            responceMeetingMap = XmlParser.runAPI(new APIGenerator().createAPI("create", "attendeePW=ap&meetingID=random-9736617&moderatorPW=mp&name=random-9736617&record=false&voiceBridge=79380&welcome=%3Cbr%3EWelcome+to+%3Cb%3E%25%25CONFNAME%25%25%3C%2Fb%3E%21"));
            //join meeting as moderator
            responceMeetingMap = XmlParser.runAPI(new APIGenerator().createAPI("join", "fullName=User+6361063&meetingID=random-9736617&password=mp"));
        } catch (TransformerException ex) {
            Logger.getLogger(APIGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(APIGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

            %>">Create Meeting</button>
    <title>JSP Page</title>
</head>
<body>
    <h1> <%
          out.print("The meeting status=" + responceJoinngMap.get("returncode"));     
          out.print("<br>Your meeting id is=" + responceJoinngMap.get("message"));
        %>
    </h1>
</body>
</html>
