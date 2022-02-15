package com.kandoka.springframework.beans.factory;

import com.kandoka.springframework.beans.BeansException;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/2/15 16:09
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

}
