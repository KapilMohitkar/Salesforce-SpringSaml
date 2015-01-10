package com.mkyong.common.controller;

import com.relecotech.bbb.api.APIGenerator;
import com.relecotech.bbb.api.XmlParser;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;






@Controller
@RequestMapping("/meeting")
public class MovieController {

	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	//public String getMovie(@PathVariable String name, ModelMap model) {
	public @ResponseBody String getMovie(@PathVariable String name) throws ParserConfigurationException {

            try {
                //model.addAttribute("meeting", name);
                
                
                
                 Map<String, String> responceMap = XmlParser.runAPI(new APIGenerator().createAPI("create", name));
                 String ss=responceMap.get("meetingID");
                 return ss;
                
            } catch (SAXException ex) {
                Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "error";
              
               

	}
	
}