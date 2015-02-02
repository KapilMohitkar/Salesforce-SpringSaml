/*
 * Copyright 2015 SpringSource.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.relecotech.common.controller;

import com.relecotech.bbb.api.APIGenerator;
import com.relecotech.bbb.api.XmlParser;
import com.relecotech.helper.SalesforceIDConverter;
import com.relecotech.helper.TimeChecker;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author kapil
 */
@Controller
@RequestMapping("/parameter")
public class AcceptParameterController {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    //public String getMovie(@PathVariable String name, ModelMap model) {
    public @ResponseBody
    ModelAndView getParameter(@PathVariable String name) throws ParserConfigurationException {
        //String param =name;
        try {
            Map<String, String> valueMap = new HashMap<String, String>();
            String[] data = name.split("&");
            for (String str : data) {
                String[] mapPair = str.split("=");
                valueMap.put(mapPair[0], mapPair[1]);
            }
            String logoutUrl = "https://" + valueMap.get("URL") + ".com";
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SAMLCredential credential = (SAMLCredential) authentication.getCredentials();

            String startTime = valueMap.get("starttime");
            String intervalTime[] = startTime.substring(0, 5).split(":");
            int hour = Integer.parseInt(intervalTime[0]);
            int minute = Integer.parseInt(intervalTime[1]);
            minute = minute + 30;
            if (minute >= 60) {
                minute = minute - 60;
                hour = hour + 1;

            }
            String endTimeForJoing = hour + ":" + minute;

            // String dateobj = new SimpleDateFormat("hh:mm a").format(new Date());
            TimeChecker application1 = new TimeChecker(valueMap.get("timeZone").replace("*", "/"));
            application1.compareStringOne = startTime;
            System.out.println("Start Time=" + application1.compareStringOne);
            application1.compareStringTwo = endTimeForJoing;
            System.out.println("End Time=" + application1.compareStringTwo);
            System.out.println("Current Time=" + application1.date);
            boolean compareDates = application1.compareDates();
            System.out.println(valueMap);
            System.out.println("time=" + compareDates);
            DateFormat df = new SimpleDateFormat("d/M/yyyy");
            TimeZone timeZone = TimeZone.getTimeZone(valueMap.get("timeZone").replace("*", "/"));
            Calendar calobj = Calendar.getInstance(timeZone);
            String today = df.format(calobj.getTime());
            System.out.println(df.format(calobj.getTime()));
            System.out.println("date by sfdc=" + valueMap.get("d").replace("*", "/"));
            if (compareDates && today.matches(valueMap.get("d").replace("*", "/"))) {
                APIGenerator aPIGenerator = new APIGenerator();
                System.out.println("converted id=" + SalesforceIDConverter.convertID(valueMap.get("code")));
                System.out.println((credential.getAttributeAsString("ContactId")));
                if (valueMap.get("code").matches(SalesforceIDConverter.convertID(credential.getAttributeAsString("ContactId")))) {
                    //if (valueMap.get("user").matches("true")) {
                    System.out.println("valuemap=" + valueMap);
                    //attendeePW=ap&meetingID=random-9736617&moderatorPW=mp&name=random-9736617
                    // String logoutUrl = java.net.URLDecoder.decode(valueMap.get("logoutURL"), "UTF-8");
                    // String logoutUrl = "https://" + valueMap.get("URL") + ".com";
                    System.out.println("logout url=" + logoutUrl);
                    String create = "attendeePW=ap" + "&meetingID=" + valueMap.get("name") + "&moderatorPW=newuser" + "&name=" + valueMap.get("name") + "&logoutURL=" + logoutUrl;
                    System.out.println("create parameter=" + create);
                    XmlParser.runAPI(aPIGenerator.createAPI("create", create));
                    // String join = "fullName=" + valueMap.get("fullName") + "&meetingID=" + valueMap.get("meetingID") + "&password=newuser";
                    String join = "fullName=" + credential.getAttributeAsString("username") + "&meetingID=" + valueMap.get("name") + "&password=newuser";
                    System.out.println("joinparam=" + join);
                    aPIGenerator.createAPI("join", join);
                    return new ModelAndView("redirect:" + aPIGenerator.apiWithChecksum);

                } // if (valueMap.get("user").matches("false")) {
                else {
                    Map<String, String> responceMap = XmlParser.runAPI(aPIGenerator.createAPI("isMeetingRunning", "meetingID=" + valueMap.get("name")));
                     String join = "fullName=" + credential.getAttributeAsString("username") + "&meetingID=" + valueMap.get("name") + "&password=ap";
                    if (responceMap.get("running").matches("true")) {
                        //String join = "fullName=" + valueMap.get("fullName") + "&meetingID=" + valueMap.get("meetingID") + "&password=" + valueMap.get("attendeePW");
//                        String join = "fullName=" + credential.getAttributeAsString("username") + "&meetingID=" + valueMap.get("meetingID") + "&password=" + valueMap.get("attendeePW");
                        aPIGenerator.createAPI("join", join);
                        return new ModelAndView("redirect:" + aPIGenerator.apiWithChecksum);
                    } else {
                        String student = "<center>  <h1>Wait! Presenter has not joined meeting yet!</h1>\n"
//                                +"<img src=\"${pageContext.request.contextPath}/images/bbbtime.jpg\" style=\"width:304px;height:228px\"/> "
                                + "     <h2>\n"
                                + "           Please try after few minutes.\n"
                                + "        \n"
                                + "<br>Date:" + valueMap.get("d").replace("*", "/") + "  Time:" + valueMap.get("starttime") + "<br><a href=" + logoutUrl + ">Back</a>"
                                + "    </h2></center> ";
                        // return new ModelAndView("redirect:" + "/student.jsp");
                        return new ModelAndView("student", "student", student);
                    }
                }

            } else {

                if (true) {
                    //  String logoutUrl = "https://" + valueMap.get("URL") + ".com";
                    //return new ModelAndView("redirect:" + "/wait/"+valueMap.get("starttime"));
                    String wait = "<center> <h1>Meeting in Future or Past!</h1><br></center>\n"
//                             +"<img src=\"${pageContext.request.contextPath}/images/bbbtime.jpg\" style=\"width:304px;height:228px\"/> "
                            + "  <center>  <h2>Meeting is not open to attendees.<br>\n"
                            + "        Please check meeting schedule time. \n<br>" + "<br>Date:" + valueMap.get("d").replace("*", "/") + "  Time:" + valueMap.get("starttime") + "<br><a href=" + logoutUrl + ">Back</a>"
                            + "    </h2></center> ";
                    return new ModelAndView("wait", "wait", wait);
                } else {

                }
            }
            //return new ModelAndView("redirect:" + "www.url.com");
//        } catch (SAXException ex) {
//            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (TransformerException ex) {
//            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(JoinMeetingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return new ModelAndView("redirect:" + aPIGenerator.apiWithChecksum);
        return null;
    }

}
