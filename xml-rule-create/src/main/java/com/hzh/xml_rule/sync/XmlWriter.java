
package com.hzh.xml_rule.sync;

import com.hzh.xml_rule.service.XmlGenerationService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * @author dahuang
 * @version : XmlWriter.java, v 0.1 2023-06-13 00:03 dahuang
 */

@Component
public class XmlWriter implements Runnable{

    @Resource
    private XmlGenerationService xmlGenerationService;


    @Override
    public void run() {
        try {
            xmlGenerationService.generateXmlFile();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}