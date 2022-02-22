package com.kandoka.springframework.beans.factory;

/**
 * @Description Bean的销毁接口类
 * @Author handong3
 * @Date 2022/2/22 17:18
 */
public interface DisposableBean {

    /**
     * 在Bean销毁后调用
     * @throws Exception
     */
    void destroy() throws Exception;
}
