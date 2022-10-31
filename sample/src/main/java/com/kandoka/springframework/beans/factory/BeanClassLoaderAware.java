package com.kandoka.springframework.beans.factory;

/**
 * @Description BeanClassLoader的感知接口类，实现此接口的类的实例可以感知到所属的ClassLoader
 * @Author handong3
 * @Date 2022/2/24 14:43
 */
public interface BeanClassLoaderAware extends Aware {
    void setBeanClassLoader(ClassLoader classLoader);
}
