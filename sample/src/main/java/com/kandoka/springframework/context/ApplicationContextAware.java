package com.kandoka.springframework.context;

import com.kandoka.springframework.beans.BeansException;
import com.kandoka.springframework.beans.factory.Aware;

/**
 * @Description Interface to be implemented by any object that wishes to be notifiedof the {@link
 *              ApplicationContext} that it runs in
 * @Author handong3
 * @Date 2022/2/24 14:46
 */
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
