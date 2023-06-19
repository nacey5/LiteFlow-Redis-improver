
package com.hzh.xml_rule.parse;

import com.hzh.xml_rule.service.XmlGenerationService;
import com.hzh.xml_rule.sync.XmlWriterAsync;
import com.yomahub.liteflow.parser.el.XmlFlowELParser;
import com.yomahub.liteflow.spi.holder.PathContentParserHolder;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RuleParser extends XmlFlowELParser {

    @Resource
    private XmlGenerationService xmlGenerationService;

    @Override
    public void parseMain(List<String> pathList) throws Exception {
        List<String> contentList = PathContentParserHolder.loadContextAware().parseContent(pathList);
        parse(contentList);
    }

    public void parseRedis() throws Exception {
        //find from local resource
        log.info("rule change from redis");
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