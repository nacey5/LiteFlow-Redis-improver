
package com.hzh.xml_rule.controller;

import com.hzh.xml_rule.service.XmlGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * @author dahuang
 * @version : CreateXmlController.java, v 0.1 2023-06-04 15:58 dahuang
 */
@RestController
@RequestMapping("/xml")
@RequiredArgsConstructor
public class CreateXmlController {

    private final XmlGenerationService xmlGenerationService;


    @GetMapping("/init")
    public String getXmlFile(){
        try {
            xmlGenerationService.generateXmlFile();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        return "success";
    }
}