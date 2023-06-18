
package com.hzh.xml_rule.controller.bean;

import com.hzh.all.annotation.CustomParameterValidation;
import com.hzh.xml_rule.manager.tran.BeanManager;
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

    @PostMapping("/register-bean/{fullClassName}/{beanName}")
    public Boolean registerBean(@PathVariable String fullClassName,@PathVariable String beanName) {
        return beanManager.registerBean(fullClassName, beanName);
    }

    @GetMapping("/get-bean/{beanName}")
    public Object getBean(@PathVariable @CustomParameterValidation String beanName) {
        return beanManager.getBeanByBeanName(beanName);
    }

    @GetMapping("/listBeans")
    public List<String> listBeans() {
        return beanManager.listAllBeanName();
    }

}