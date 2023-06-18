package com.hzh.xml_rule.controller.bean;

/**
 * @author dahuang
 * @version : JavaFileUploadController.java, v 0.1 2023-06-14 14:24 dahuang
 */

import com.hzh.all.annotation.CustomMethodValidation;
import com.hzh.xml_rule.manager.tran.FileManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class JavaFileUploadController {

    @Resource
    private FileManager fileManager;

    @PostMapping("/upload/{fullClassName}")
    @CustomMethodValidation
    public Boolean uploadJavaFile(@RequestBody String javaCode, @PathVariable String fullClassName) {
        return fileManager.uploadJavaFile(javaCode, fullClassName);
    }

}
