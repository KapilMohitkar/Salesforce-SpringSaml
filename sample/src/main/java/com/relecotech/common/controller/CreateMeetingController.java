package com.relecotech.common.controller;

import com.relecotech.bbb.api.APIGenerator;
import com.relecotech.bbb.api.XmlParser;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/createmeeting")
public class CreateMeetingController {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    //public String getMovie(@PathVariable String name, ModelMap model) {
    public @ResponseBody String getMovie(@PathVariable String name) throws ParserConfigurationException {
        String ss = "failed";
        try {
            //model.addAttribute("meeting", name);
            APIGenerator aPIGenerator = new APIGenerator();
            // Map<String, String> forwardCallMap = null;
           //  forwardCallMap.put(name,"attendeePW=ap&meetingID=random-9736617&moderatorPW=mp");
           //  String param=forwardCallMap.get(name);
           Map<String, String> responceMap = XmlParser.runAPI( aPIGenerator.createAPI("create",name));
           // aPIGenerator.createAPI("join", name);
            // ss = responceMap.get("meetingID");
          //  System.out.println("in the class.....value=" + aPIGenerator.apiWithChecksum);
            // aPIGenerator.apiWithChecksum;
            System.out.println("mmmmmmmmmmmmm="+responceMap.get("meetingID"));
           // return new ModelAndView("redirect:" + aPIGenerator.apiWithChecksum);  
//        } catch (SAXException ex) {
//            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (TransformerException ex) {
//            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CreateMeetingController.class.getName()).log(Level.SEVERE, null, ex);
        }
       //return new ModelAndView("redirect:" + aPIGenerator.apiWithChecksum);
return name;
    }
    

}
