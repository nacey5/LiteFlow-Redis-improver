
package com.hzh.xml_rule.controller;

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
    private BeanDefinitionRegistry beanDefinitionRegistry;
    @Resource
    private ApplicationContext applicationContext;

    @PostMapping("/register-bean/{fullClassName}/{beanName}")
    public String registerBean(@PathVariable String fullClassName,@PathVariable String beanName) {
        try {
            if (beanDefinitionRegistry.containsBeanDefinition(beanName)) {
                return "Bean already registered: " + beanName;
            }
            Class<?> beanClass = Class.forName(fullClassName);
            // 创建新的 BeanDefinition
            BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(beanClass).getBeanDefinition();
            // 注册新的 BeanDefinition
            beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
            Object o = beanClass.getDeclaredConstructor().newInstance();

            ((ConfigurableApplicationContext) applicationContext).getBeanFactory()
                .registerSingleton(beanName, o);

            return "Bean registered successfully: " + beanName;
        } catch (ClassNotFoundException e) {
            return "Failed to register Bean. Class not found: " + fullClassName;
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get-bean/{beanName}")
    public String getBean(@PathVariable String beanName)
        throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
        IllegalAccessException {
        Object bean = applicationContext.getBean(beanName);
        if (bean != null) {
            return "Bean found: " + bean.toString();
        } else {
            return "Bean not found: " + beanName;
        }
    }

    @GetMapping("/listBeans")
    public List<String> listBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        return Arrays.asList(beanNames);
    }

}