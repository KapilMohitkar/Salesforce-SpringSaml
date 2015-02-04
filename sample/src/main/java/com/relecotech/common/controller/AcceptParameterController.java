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
import com.relecotech.helper.AddSubtractTime;
import com.relecotech.helper.SalesforceIDConverter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
            TimeZone timeZone = TimeZone.getTimeZone(valueMap.get("timeZone").replace("*", "/"));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");

            Calendar calobj = Calendar.getInstance(timeZone);
            String SystemCurrentTime = dateFormat.format(calobj.getTime());
            Date SystemCurrentDateTime = dateFormat.parse(SystemCurrentTime);

            //SFDC Meeting DATE/TIME 
            String sfdcMeetingTime = valueMap.get("d").replace("*", "/");
            System.out.println("sfdcMeetingTime=" + sfdcMeetingTime);
            Date sfdcMeetingDateTime = dateFormat.parse(sfdcMeetingTime);
            //TIME Before Meeting can satarted 
            Date sfdcMeetingBeforeDateTime = dateFormat.parse(AddSubtractTime.getSubtractedTime(sfdcMeetingTime, timeZone));
            //TIME after Meeting cannot be satarted 
            Date sfdcMeetingAfterDateTime = dateFormat.parse(AddSubtractTime.getAddedTime(sfdcMeetingTime, timeZone));

            System.out.println("SystemCurrentDateTime=" + SystemCurrentDateTime);
            System.out.println("sfdcMeetingDateTime=" + sfdcMeetingDateTime);
            System.out.println("sfdcMeetingAfterDateTime=" + sfdcMeetingAfterDateTime);
            System.out.println("sfdcMeetingBeforeDateTime=" + sfdcMeetingBeforeDateTime);
            if (SystemCurrentDateTime.after(sfdcMeetingBeforeDateTime) && SystemCurrentDateTime.before(sfdcMeetingAfterDateTime)) {
                APIGenerator aPIGenerator = new APIGenerator();
                System.out.println("converted id=" + SalesforceIDConverter.convertID(valueMap.get("code")));
                System.out.println((credential.getAttributeAsString("ContactId")));
                if (valueMap.get("code").matches(SalesforceIDConverter.convertID(credential.getAttributeAsString("ContactId")))) {

                    System.out.println("valuemap=" + valueMap);

                    System.out.println("logout url=" + logoutUrl);
                    String create = "attendeePW=ap" + "&meetingID=" + valueMap.get("name") + "&moderatorPW=newuser" + "&name=" + valueMap.get("name") + "&logoutURL=" + logoutUrl;
                    System.out.println("create parameter=" + create);
                    XmlParser.runAPI(aPIGenerator.createAPI("create", create));
                    // String join = "fullName=" + valueMap.get("fullName") + "&meetingID=" + valueMap.get("meetingID") + "&password=newuser";
                    String join = "fullName=" + credential.getAttributeAsString("username") + "&meetingID=" + valueMap.get("name") + "&password=newuser";
                    System.out.println("joinparam=" + join);
                    aPIGenerator.createAPI("join", join);
                    return new ModelAndView("redirect:" + aPIGenerator.apiWithChecksum);

                } else {
                    Map<String, String> responceMap = XmlParser.runAPI(aPIGenerator.createAPI("isMeetingRunning", "meetingID=" + valueMap.get("name")));
                    String join = "fullName=" + credential.getAttributeAsString("username") + "&meetingID=" + valueMap.get("name") + "&password=ap";
                    if (responceMap.get("running").matches("true")) {

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
//                
                if (SystemCurrentDateTime.compareTo(sfdcMeetingDateTime) == -1) {

                    String wait = "<center> <h1>Too Early for Meeting!</h1><br></center>\n"
                            //                             +"<img src=\"${pageContext.request.contextPath}/images/bbbtime.jpg\" style=\"width:304px;height:228px\"/> "
                            + "  <center>  <h2>Meeting is not yet open.<br>\n"
                            + "        Please check meeting schedule time. \n<br>" + "<br>Meeting Date/Time:" + sfdcMeetingDateTime + "</h2><h3><br>System Current Date/Time:" + SystemCurrentDateTime + "<br><a href=" + logoutUrl + ">Back</a>"
                            + "    *Meeting will open 15 minutes before scheduled time</h3></center> ";
                    return new ModelAndView("wait", "wait", wait);
                }
                //for Past Date
                if (SystemCurrentDateTime.compareTo(sfdcMeetingDateTime) == 1) {
                    String wait = "<center> <h1>Meeting is Over!</h1><br></center>\n"
                            //                             +"<img src=\"${pageContext.request.contextPath}/images/bbbtime.jpg\" style=\"width:304px;height:228px\"/> "
                            + "  <center>  <h2>Meeting can not open.<br>\n"
                            + "        Meeting was scheduled on - \n<br>" + "<br>Meeting Date/Time:" + sfdcMeetingDateTime + "</h2><h3><br>System Current Date/Time:" + SystemCurrentDateTime + "<br><a href=" + logoutUrl + ">Back</a>"
                            + "  </h3>  </center> ";
                    return new ModelAndView("wait", "wait", wait);

                }
//                
            }

        } catch (Exception ex) {
            System.out.println("Exception="+ex);
        }

        return null;
    }

}
