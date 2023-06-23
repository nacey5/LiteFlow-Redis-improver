package com.hzh.xml_rule.component;

import com.hzh.all.GrayScheduling;
import com.hzh.all.annotation.LiteComponent;
import com.hzh.all.request.TagConsider;
import com.hzh.xml_rule.manager.BaseManager;
import com.hzh.xml_rule.util.NodeColorCacheFactory;
import com.yomahub.liteflow.annotation.LiteflowRetry;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author dahuang
 * @version : ACmp.java, v 0.1 2023-06-11 14:38 dahuang
 */
@LiteComponent("a")
//@Component("a")
@Slf4j
public class ACmp extends NodeComponent implements BaseManager, TagConsider {

    //写一个单例:也可以不使用单例 在初始化构建的时候进行构建
    Map<GrayScheduling, String> graySchedulingStringMap;

    public ACmp() {
        this.graySchedulingStringMap = NodeColorCacheFactory.buildNewCacheMap();
    }

    @Override
    public void beforeProcess() {
        super.beforeProcess();
        //todo 对标志进行识别并进行统一的插入
        Class<? extends ACmp> clazz = this.getClass();
        if (clazz.isAnnotationPresent(LiteComponent.class)) {
            LiteComponent annotation = clazz.getAnnotation(LiteComponent.class);
            GrayScheduling tag = annotation.tag();
            considerTag(tag);
        }
        //如果没有这个标签，可以识别为其他的bean类，不做其他处理，只执行普通业务
    }

    @Override
    public void process() {
        //do your business
        System.out.println("a");
    }

    @Override
    public void afterProcess() {
        super.afterProcess();
    }

    @Override
    public void logic() {

    }

    @Override
    public void considerTag(GrayScheduling scheduling) {
        switch (scheduling) {
            case BLUE:
                log.info("blue cure");
                break;
            case GREEN:
                log.info("green cure");
                break;
            default:
                log.info("default cure");
        }
    }
}