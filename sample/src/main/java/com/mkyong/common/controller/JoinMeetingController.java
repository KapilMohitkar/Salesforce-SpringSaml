package com.mkyong.common.controller;

import com.relecotech.bbb.api.APIGenerator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/joinmeeting")
public class JoinMeetingController {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    //public String getMovie(@PathVariable String name, ModelMap model) {
    public @ResponseBody ModelAndView getMovie(@PathVariable String name) throws ParserConfigurationException {
        String ss = "failed";
        try {
            //model.addAttribute("meeting", name);
            APIGenerator aPIGenerator = new APIGenerator();
            //Map<String, String> responceMap = XmlParser.runAPI(aPIGenerator.createAPI("join", name));
            aPIGenerator.createAPI("join", name);
            // ss = responceMap.get("meetingID");
            System.out.println("in the class.....value=" + aPIGenerator.apiWithChecksum);
            // aPIGenerator.apiWithChecksum;
            return new ModelAndView("redirect:" + aPIGenerator.apiWithChecksum);  
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
