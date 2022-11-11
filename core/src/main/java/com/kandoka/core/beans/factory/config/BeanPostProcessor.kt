package com.kandoka.core.beans.factory.config

/**
 *
 *  @Description <p>BeanPostProcessor 可以在 spring 容器实例化 bean 之后，在执行 bean 的初始化方法前后，添加一些自己的处理逻辑。 这里说的初始化方法，指的是以下两种：
 * <ol>
 * <li>bean 实现 了 InitializingBean 接口，对应的方法为 afterPropertiesSet 。
 * <li>在 XML 文件中定义 bean 的时候，标签有个属性叫做  init-method，来指定初始化方法。
 * </ol>
 * <p>注意：BeanPostProcessor 是在 spring 容器加载了 bean 的定义文件并且实例化 bean 之后执行的。
 * BeanPostProcessor 的执行顺序是在 BeanFactoryPostProcessor 之后。
 *  @Author handong3
 *  @Date 2022/11/9 18:38
 *
 */
interface BeanPostProcessor {
}