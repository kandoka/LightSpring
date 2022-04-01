package com.kandoka.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.kandoka.springframework.beans.factory.config.BeanDefinition;
import com.kandoka.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Description 处理对象扫描装配类
 * @Author handong3
 * @Date 2022/4/1 15:13
 */
public class ClassPathScanningCandidateComponentProvider {
    /**
     * 通过这个方法就可以扫描到所有 @Component 注解的类 作为Bean定义了。
     * @param basePackage
     * @return
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage){
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for(Class<?> clazz: classes){
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }
}
