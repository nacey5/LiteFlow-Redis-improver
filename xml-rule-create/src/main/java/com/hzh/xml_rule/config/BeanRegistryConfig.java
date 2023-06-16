
package com.hzh.xml_rule.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dahuang
 * @version : BeanRegistryConfig.java, v 0.1 2023-06-16 14:35 dahuang
 */
@Configuration
public class BeanRegistryConfig {

    @Bean
    public BeanDefinitionRegistry beanDefinitionRegistry() {
        return new DefaultListableBeanFactory();
    }
}