package com.kandoka.springframework.beans.factory;

/**
 * @Description bean的初始化接口类
 * @Author handong3
 * @Date 2022/2/22 17:15
 */
public interface InitializingBean {

    /**
     * 在Bean属性注入后调用
     * @throws Exception
     */
    void afterPropertySet() throws Exception;
}
