package com.kandoka.springframework.beans.factory.support;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.DisposableBean;
import com.kandoka.springframework.beans.factory.ObjectFactory;
import com.kandoka.springframework.beans.factory.config.SingletonBeanRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 单例bean缓存的实现，负责bean单例缓存的注册、获取
 * @Author handong3
 * @Date 2022/2/15 16:24
 */
@Slf4j
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 一级缓存，普通实例化完成后的对象
     */
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * 二级缓存，提前暴漏对象，没有完全实例化的对象
     */
    protected final Map<String, Object> earlySingletonObjects = new HashMap<>();

    /**
     * 三级缓存，存放代理对象
     */
    private final Map<String, ObjectFactory<?>> singletonObjectFactories = new HashMap<>();

    protected static final Object NULL_OBJECT = new Object();

    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

    /**
     * 从bean缓存获取bean
     * @param beanName bean名称
     * @return
     */
    @Override
    public Object getSingleton(String beanName) {
        log.info("[{}] getSingleton开始", beanName);
        Object singletonObject = singletonObjects.get(beanName);
        //判断一级缓存中是否有对象
        if(null == singletonObject){
            log.info("从第一级缓存未获取到"+beanName+"，尝试从二级缓存获取的半成品对象");
            singletonObject = earlySingletonObjects.get(beanName);
            //判断二级缓存中是否有对象。没有的话，这个对象就是代理对象，因为只有代理对象才会放到三级缓存中
            if(null == singletonObject){
                log.info("从第二级缓存未获取到"+beanName+"，尝试从三级缓存的代理对象");
                ObjectFactory<?> singletonFactory = singletonObjectFactories.get(beanName);
                if(null != singletonFactory){
                    log.info("从第三级级缓存获取到"+beanName+"的代理对象，放入到二级缓存中");
                    singletonObject = singletonFactory.getObject();
                    //把三级缓存中的代理对象中的真实对象获取出来，放入到二级缓存中
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonObjectFactories.remove(beanName);
                } else {
                    log.info("从第三级级缓存未获取"+beanName+"的代理对象");
                }
            } else {
                log.info("从第二级级缓存获取到"+beanName+"的半成品对象");
            }
        } else {
            log.info("从第一级缓存获取到"+beanName+"的实例对象");
        }
        log.info("[{}] getSingleton完成，引用地址：{}", beanName, singletonObject);
        return singletonObject;
    }

    public void destroySingletons() {
        log.info("DefaultSingletonBeanRegistry.destroySingletons() 开始");
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
        log.info("DefaultSingletonBeanRegistry.destroySingletons() 完成");
    }

    /**
     * 注册单例
     * @param beanName
     * @param singletonObject
     */
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonObjectFactories.remove(beanName);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        if(!this.singletonObjects.containsKey(beanName)){
            this.singletonObjectFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }


    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

}
