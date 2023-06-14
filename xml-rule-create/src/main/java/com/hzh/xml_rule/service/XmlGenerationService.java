/*
 * Aloudata.com Inc.
 * Copyright (c) 2021-2023 All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hzh.xml_rule.service;

import com.hzh.liteflow_redis.service.RedisService;
import com.hzh.xml_rule.base.Rule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.transform.OutputKeys;

/**
 * @author dahuang
 * @version : XmlGenerationService.java, v 0.1 2023-06-04 14:34 dahuang
 */
@Service
public class XmlGenerationService {


    @Resource
    private  RedisService redisService;

    public void generateXmlFile() throws ParserConfigurationException, TransformerException {

        // 创建XML文档
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        // 创建根元素
        Element rootElement = doc.createElement("flow");
        doc.appendChild(rootElement);

        for (String key : redisService.keys()) {
            Element chainElement = doc.createElement("chain");
            chainElement.setAttribute("name",key);
            rootElement.appendChild(chainElement);
            String value = redisService.getValue(key);
            chainElement.appendChild(doc.createTextNode(value));
        }

        // 将XML文档写入文件
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        File xmlFile = new File("xml-rule-create/src/main/resources/rule.xml"); // 指定生成的XML文件路径
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
    }

    public String  generateXmlFileAndReturn() throws ParserConfigurationException, TransformerException, IOException {

        // 创建XML文档
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        // 创建根元素
        Element rootElement = doc.createElement("flow");
        doc.appendChild(rootElement);

        for (String key : redisService.keys()) {
            Element chainElement = doc.createElement("chain");
            chainElement.setAttribute("name",key);
            rootElement.appendChild(chainElement);
            String value = redisService.getValue(key);
            chainElement.appendChild(doc.createTextNode(value));
        }

        // 将XML文档写入文件
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        File xmlFile = new File("xml-rule-create/src/main/resources/rule.xml"); // 指定生成的XML文件路径
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
        //==========================================================
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        String ret="";
        StringWriter writer=null;
        try {
             writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            ret= writer.toString();
        }finally {
            assert writer != null;
            writer.close();
        }
       return ret;
    }
}