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
import com.relecotech.helper.TimeChecker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
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
            TimeChecker application1 = new TimeChecker();
            application1.compareStringOne = startTime;
            System.out.println("Start Time=" + application1.compareStringOne);
            application1.compareStringTwo = endTimeForJoing;
            System.out.println("End Time=" + application1.compareStringTwo);
            System.out.println("Current Time=" + application1.date);
            boolean compareDates = application1.compareDates();
            System.out.println(valueMap);
            System.out.println("time="+compareDates);
            if (compareDates) {
                APIGenerator aPIGenerator = new APIGenerator();
                if (valueMap.get("user").matches("true")) {
                    System.out.println("valuemap=" + valueMap);
                    //attendeePW=ap&meetingID=random-9736617&moderatorPW=mp&name=random-9736617
                    String create = "attendeePW=ap"+"&meetingID=" + valueMap.get("meetingID") + "&moderatorPW=newuser" + "&name=" + valueMap.get("name");
                    System.out.println("create parameter=" + create);
                    XmlParser.runAPI(aPIGenerator.createAPI("create", create));
                    String join = "fullName=" + valueMap.get("fullName") + "&meetingID="+valueMap.get("meetingID")+ "&password=newuser" ;
                    System.out.println("joinparam=" + join);
                    aPIGenerator.createAPI("join", join);
                   // String urlForStudent=aPIGenerator.apiWithChecksum;
                    return new ModelAndView("redirect:" + aPIGenerator.apiWithChecksum);

                } 
                if(valueMap.get("user").matches("false")){
                    Map<String, String> responceMap = XmlParser.runAPI(aPIGenerator.createAPI("isMeetingRunning", "meetingID=" + valueMap.get("meetingID")));
                    if (responceMap.get("running").matches("true")) {
                        String join = "fullName=" + valueMap.get("fullName") + "&meetingID=" + valueMap.get("meetingID") + "&password="+ valueMap.get("attendeePW") ;
                        aPIGenerator.createAPI("join", join);
                        return new ModelAndView("redirect:" + aPIGenerator.apiWithChecksum);
                    }
                    else{
                        return new ModelAndView("redirect:" + "/student.jsp");
                    }
                }

            } else {

                return new ModelAndView("redirect:" + "/wait.jsp");
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
