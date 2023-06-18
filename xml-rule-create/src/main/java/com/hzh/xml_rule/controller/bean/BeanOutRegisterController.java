
package com.hzh.xml_rule.controller.bean;

import com.hzh.all.annotation.CustomMethodValidation;
import com.hzh.all.annotation.CustomParameterValidation;
import com.hzh.xml_rule.manager.tran.BeanManager;
import com.hzh.xml_rule.request.BeanRequest;
import com.hzh.xml_rule.request.RegisterBeanRequest;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.spring.ComponentScanner;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * @author dahuang
 * @version : BeanOutRegisterController.java, v 0.1 2023-06-16 14:27 dahuang
 */

@RestController
@RequestMapping("/beans")
public class BeanOutRegisterController {

    @Resource
    private BeanManager beanManager;

    @PostMapping("/register-bean")
    @CustomMethodValidation
    public Boolean registerBean(@RequestBody RegisterBeanRequest registerBeanRequest) {
        registerBeanRequest.check();
        return beanManager.registerBean(registerBeanRequest.getFullClassName(),
            registerBeanRequest.getBeanName());
    }

    @GetMapping("/get-bean")
    public Object getBean(@RequestBody BeanRequest beanRequest) {
        beanRequest.check();
        return beanManager.getBeanByBeanName(beanRequest.getBeanName());
    }

    @GetMapping("/listBeans")
    public List<String> listBeans() {
        return beanManager.listAllBeanName();
    }

}