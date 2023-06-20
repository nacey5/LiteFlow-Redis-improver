
package com.hzh.xml_rule.manager.tran.impl;

import com.hzh.all.annotation.CustomMethodValidation;
import com.hzh.xml_rule.manager.tran.BeanManager;
import com.hzh.xml_rule.service.IBeanService;
import com.yomahub.liteflow.core.FlowExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * @author dahuang
 * @version : BeanManagerImpl.java, v 0.1 2023-06-18 13:22 dahuang
 */
@Component
@Slf4j
public class BeanManagerImpl implements BeanManager {

    @Resource
    private BeanDefinitionRegistry beanDefinitionRegistry;
    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private FlowExecutor flowExecutor;

    @Resource
    private IBeanService beanService;


    @Override
    @CustomMethodValidation
    public Boolean registerBean(String fullClassName, String beanName) {
        try {
            if (beanDefinitionRegistry.containsBeanDefinition(beanName)) {
                return false;
            }
            Object o = registerBeanAndGet(fullClassName, beanName);
            beanService.scanIntoSpringboot(o,beanName);
            flowExecutor.reloadRule();
            log.info("Bean registered successfully: {}", beanName);
            return true;
        } catch (ClassNotFoundException e) {
            log.warn("Failed to register Bean. Class not found: {}", fullClassName);
            return false;
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

    @Override
    public String  getBeanByBeanName(String beanName) {
        Object bean = applicationContext.getBean(beanName);
        if (bean != null) {
            log.info("Bean found: {}",beanName);
            return beanName;
        } else {
            log.warn("Bean not found:{} ",beanName);
            throw new RuntimeException("the bean "+beanName+"not found");
        }
    }

    @Override
    public List<String> listAllBeanName() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        return Arrays.asList(beanNames);
    }

    private Object registerBeanAndGet(String fullClassName, String beanName)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
        NoSuchMethodException {
        Class<?> beanClass = Class.forName(fullClassName);
        // 创建新的 BeanDefinition
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(beanClass).getBeanDefinition();
        // 注册新的 BeanDefinition
        beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
        Object o = beanClass.getDeclaredConstructor().newInstance();

        ((ConfigurableApplicationContext)applicationContext).getBeanFactory().registerSingleton(beanName, o);
        return o;
    }
}