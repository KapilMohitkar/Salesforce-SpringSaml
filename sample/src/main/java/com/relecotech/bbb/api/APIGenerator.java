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
import javax.xml.transform.TransformerException;
import org.apache.commons.codec.digest.*;

/**
 *
 * @author firstuser
 */
public class APIGenerator {

 //   public static final String SALT = "3cba79d28212d86d3a6181d6d9ed1308";
   // public static final String SALT = "8cd8ef52e8e101574e400365b55e11a6";//online server salt
    public static final String SALT = "f236b571633c0028418918ee38486c2a";//Azure server salt
    public String api;
    private String server;
    private String action;
    private String checksum;
    public String apiWithChecksum;

    public APIGenerator() {

        //this.server = "http://192.168.1.31:8080/bigbluebutton/api/";
        
     //   this.server = "http://test-install.blindsidenetworks.com/bigbluebutton/api/";//online servercommand to get=sudo bbb-conf --secret
        this.server = "http://bbs-prod.cloudapp.net/bigbluebutton/api/";//azure server

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
        System.out.println("final api to call="+apiWithChecksum);

        return apiWithChecksum;

    }

    public static void main(String[] args) {
       
        try {
         XmlParser.runAPI(new APIGenerator().createAPI("create", "attendeePW=ap&meetingID=random-9736617&moderatorPW=mp&name=random-9736617&record=false&voiceBridge=79380&welcome=%3Cbr%3EWelcome+to+%3Cb%3E%25%25CONFNAME%25%25%3C%2Fb%3E%21"));
            //join moderator
         // XmlParser.runAPI(new APIGenerator().createAPI("join", "fullName=User+6361063&meetingID=random-9736617&password=mp"));
      //   XmlParser.runAPI(new APIGenerator().createAPI("join", "fullName=User+6361063&meetingID=random-9736617&password=ap"));
         // XmlParser.runAPI(new APIGenerator().createAPI("isMeetingRunning", "meetingID=random-9736617"));
         // XmlParser.runAPI(new APIGenerator().createAPI("getMeetingInfo", "meetingID=random-9736617&password=mp"));
        //  XmlParser.runAPI(new APIGenerator().createAPI("end", "meetingID=random-9736617&password=mp"));
          //XmlParser.runAPI(new APIGenerator().createAPI("getMeetings", ""));
         // XmlParser.runAPI(new APIGenerator().createAPI("getDefaultConfigXML", ""));
         // XmlParser.runAPI(new APIGenerator().createAPI("getRecordings", "meetingID=random-9736617"));
         // XmlParser.runAPI(new APIGenerator().createAPI("publishRecordings", "publish=false&recordID=random-9736617"));
         // XmlParser.runAPI(new APIGenerator().createAPI("deleteRecordings", "recordID=random-9736617"));
         //join from mobile (as moderator
       //  XmlParser.runAPI(new APIGenerator().createAPI("join", "fullName=User+6361063&meetingID=random-9736617&password=mp"));
         //join from mobile (as attendee) 
        // XmlParser.runAPI(new APIGenerator().createAPI("join", "fullName=User+6361063&meetingID=random-9736617&password=ap"));
        } catch (TransformerException ex) {
            Logger.getLogger(APIGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(APIGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
//http://192.168.1.31:8080/bigbluebutton/api/?joinfullName=User+6361063&meetingID=random-9736617&password=mp3cba79d28212d86d3a6181d6d9ed1308&checksum=d7ba4c76c5c0e7342bac4f08e2895f14db5584f9
 
//root http://test-install.blindsidenetworks.com/bigbluebutton/api/?checksum=922f2c0d71afabb6ea78279e8bea2fef2c93b76b
//post create http://test-install.blindsidenetworks.com/bigbluebutton/api/create?attendeePW=ap&meetingID=random-9736617&moderatorPW=mp&name=random-9736617&record=false&voiceBridge=79380&welcome=%3Cbr%3EWelcome+to+%3Cb%3E%25%25CONFNAME%25%25%3C%2Fb%3E%21&checksum=6ba4b5be2ed7c7f098b27e4c2443d14c222dde4d
//post join (as moderator) http://test-install.blindsidenetworks.com/bigbluebutton/api/join?fullName=User+6361063&meetingID=random-9736617&password=mp&checksum=6941709bb09804741359c9997db611ff930c8398
//post join (as attendee) http://test-install.blindsidenetworks.com/bigbluebutton/api/join?fullName=User+6361063&meetingID=random-9736617&password=ap&checksum=3fc5ff0bd3a87a2623e5384b096a0a73282778a6
//post isMeetingRunning http://test-install.blindsidenetworks.com/bigbluebutton/api/isMeetingRunning?meetingID=random-9736617&checksum=eee15e99d95cffd71ff38828f016b82884c5df9c
//post getMeetingInfo http://test-install.blindsidenetworks.com/bigbluebutton/api/getMeetingInfo?meetingID=random-9736617&password=mp&checksum=532f7695411e043670b5b450065dcb267cd331aa
//post end http://test-install.blindsidenetworks.com/bigbluebutton/api/end?meetingID=random-9736617&password=mp&checksum=e4a6a5808c7b1ad1a168747df2d61fda82a6eba6
//post getMeetings http://test-install.blindsidenetworks.com/bigbluebutton/api/getMeetings?checksum=d23fef405937517be465ffccae12d5c1103a5e00
//post getDefaultConfigXML http://test-install.blindsidenetworks.com/bigbluebutton/api/getDefaultConfigXML?checksum=5a9fb2b8d056a63a7c735d30235e6c39397a160b
//post setConfigXML http://test-install.blindsidenetworks.com/bigbluebutton/api/setConfigXML?meetingID=random-9736617&checksum=3b6e399a82467588fcfd5a5cdb7b25d1dfa8180d
//post getRecordings http://test-install.blindsidenetworks.com/bigbluebutton/api/getRecordings?meetingID=random-9736617&checksum=309496c7f3041dd17973af8dca509a35fcf0d242
//post publishRecordings http://test-install.blindsidenetworks.com/bigbluebutton/api/publishRecordings?publish=false&recordID=random-9736617&checksum=fc7e856e1b37d74561c1313b4dbb21350d891e53
//post deleteRecordings http://test-install.blindsidenetworks.com/bigbluebutton/api/deleteRecordings?recordID=random-9736617&checksum=d913eeb7eb1e39718fd672b35f797d349f91100a
//post join from mobile (as moderator) bigbluebutton://test-install.blindsidenetworks.com/bigbluebutton/api/join?fullName=User+6361063&meetingID=random-9736617&password=mp&checksum=6941709bb09804741359c9997db611ff930c8398
//post join from mobile (as attendee) bigbluebutton://test-install.blindsidenetworks.com/bigbluebutton/api/join?fullName=User+6361063&meetingID=random-9736617&password=ap&checksum=3fc5ff0bd3a87a2623e5384b096a0a73282778a6


//http://192.168.1.31:8080/bigbluebutton/api/create?attendeePW=ap&meetingID=random-9736617&moderatorPW=mp&name=random-9736617&record=false&voiceBridge=79380&welcome=%3Cbr%3EWelcome+to+%3Cb%3E%25%25CONFNAME%25%25%3C%2Fb%3E%21&checksum=0974b85a135c1abe11f1cacdcf215c5d3e7e50cc
//http://192.168.1.31:8080/bigbluebutton/api/join?fullName=User+6361063&meetingID=random-9736617&password=mp&checksum=d7ba4c76c5c0e7342bac4f08e2895f14db5584f9