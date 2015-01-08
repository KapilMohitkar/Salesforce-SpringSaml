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



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParser {

    public static  Map<String, String> runAPI(String bbburl) throws IOException, ParserConfigurationException, SAXException, TransformerException, Exception {
       
       // URL url = new URL("http://localhost:8080/bigbluebutton/api/create?name=Test+Meeting&meetingID=abc123&attendeePW=111222&moderatorPW=333444&checksum=7defcfc95b5740f64c6b4556f4fb90842cc5676f");
        URL url = new URL(bbburl);
        HttpURLConnection request1 = (HttpURLConnection) url.openConnection();
        request1.setRequestMethod("GET");
        request1.connect();
        InputStream is = request1.getInputStream();
        String inputStreamString = new Scanner(is, "UTF-8").useDelimiter("\\A").next();

        Map<String, String> responceMap = new HashMap<String, String>();
        responceMap = convertNodesFromXml(inputStreamString);
        System.out.println("map=" + responceMap);
        System.out.println(responceMap.get("meetingID"));
        return responceMap;
    }

    public static Map<String, String> convertNodesFromXml(String xml) throws Exception {

        InputStream is = new ByteArrayInputStream(xml.getBytes());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(is);
        return createMap(document.getDocumentElement());
    }

    public static Map<String, String> createMap(Node node) {
        Map<String, String> map = new HashMap<String, String>();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.hasAttributes()) {
                for (int j = 0; j < currentNode.getAttributes().getLength(); j++) {
                    Node item = currentNode.getAttributes().item(i);
                    map.put(item.getNodeName(), item.getTextContent());
                }
            }
            if (node.getFirstChild() != null && node.getFirstChild().getNodeType() == Node.ELEMENT_NODE) {
                map.putAll(createMap(currentNode));
            } else if (node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                map.put(node.getLocalName(), node.getTextContent());
            }
        }
        return map;
    }

    
}
