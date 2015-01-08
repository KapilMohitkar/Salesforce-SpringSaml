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
package com.relecotech.bbb.api;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.commons.codec.digest.*;
import org.xml.sax.SAXException;

/**
 *
 * @author firstuser
 */
public class APIGenerator {

    public static final String SALT = "3cba79d28212d86d3a6181d6d9ed1308";
    public String api;
    private String server;
    private String action;
    private String checksum;
    public String apiWithChecksum;

    public APIGenerator() {

        this.server = "http://192.168.1.31:8080/bigbluebutton/api/";

    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String createAPI(String action, String parameter) {

        api = action + parameter + SALT;
        System.out.println("api=" + api);
        String md6 = DigestUtils.shaHex(api);
        System.out.println("md6=" + md6);
        checksum = "&checksum=" + DigestUtils.shaHex(api);
        // api="createname=Test+Meeting&meetingID=abc123&attendeePW=111222&moderatorPW=333444"+SALT;
        apiWithChecksum = server + action + "?" + parameter + checksum;

        return apiWithChecksum;

    }

    public static void main(String[] args) {
        try {
            // XmlParser parser=new XmlParser(new APIGenerator().createAPI("create", "name=Test+Meeting&meetingID=abc123&attendeePW=111222&moderatorPW=333444"));
            XmlParser.runAPI(new APIGenerator().createAPI("create", "name=Test+Meeting&meetingID=abc123&attendeePW=111222&moderatorPW=333444"));
        } catch (TransformerException ex) {
            Logger.getLogger(APIGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(APIGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
