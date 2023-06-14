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
package com.hzh.xml_rule.parse;

import com.hzh.xml_rule.service.XmlGenerationService;
import com.hzh.xml_rule.sync.XmlWriterAsync;
import com.yomahub.liteflow.parser.el.XmlFlowELParser;
import com.yomahub.liteflow.spi.holder.PathContentParserHolder;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dahuang
 * @version : MyParser.java, v 0.1 2023-06-11 19:20 dahuang
 */

@Component
public class MyParser extends XmlFlowELParser {

    @Resource
    private XmlGenerationService xmlGenerationService;

    @Override
    public void parseMain(List<String> pathList) throws Exception {
        List<String> contentList = PathContentParserHolder.loadContextAware().parseContent(pathList);
        parse(contentList);
    }

    public void parseRedis() throws Exception {
        //find from local resource
        List<String> contentList = new ArrayList<>();
        if (!extracted(contentList)) {
            parse(contentList);
            XmlWriterAsync.writeAsync();
        }
    }

    public void parseLocalXml(List<String> pathList) throws Exception {
        //find from local resource
        List<String> contentList = new ArrayList<>();
        try {
            contentList = PathContentParserHolder.loadContextAware().parseContent(pathList);
        } catch (Exception e) {
        } finally {
            if (!extracted(contentList)) {
                parse(contentList);
            }
        }
    }

    private boolean extracted(List<String> contentList)
        throws ParserConfigurationException, TransformerException, IOException {
        if (contentList.isEmpty()) {
            //get from redis and save to the local
            String content = xmlGenerationService.generateXmlFileAndReturn();
            if (StringUtils.isBlank(content)) {
                return true;
            }
            contentList.add(content);
        }
        return false;
    }
}