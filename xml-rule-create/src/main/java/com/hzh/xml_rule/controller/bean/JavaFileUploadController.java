package com.hzh.xml_rule.controller.bean;

/**
 * @author dahuang
 * @version : JavaFileUploadController.java, v 0.1 2023-06-14 14:24 dahuang
 */

import com.hzh.all.annotation.CustomMethodValidation;
import com.hzh.xml_rule.manager.tran.FileManager;
import com.hzh.xml_rule.request.CodeRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/code")
public class JavaFileUploadController {

    @Resource
    private FileManager fileManager;

    @PostMapping("/upload")
    @CustomMethodValidation
    public Boolean uploadJavaFile(@RequestBody CodeRequest codeRequest) {
        codeRequest.check();
        return fileManager.uploadJavaFile(codeRequest.getJavaCode(),
            codeRequest.getFullClassName());
    }

}
