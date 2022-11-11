package com.kandoka.core.beans.factory

/**
 *
 *  @Description Sub-interface implemented by bean factories that can be part
 * of a hierarchy.
 *
 *
 * The corresponding `setParentBeanFactory` method for bean
 * factories that allow setting the parent in a configurable
 * fashion can be found in the ConfigurableBeanFactory interface.
 *
 *  @Author handong3
 *  @Date 2022/11/9 18:13
 *
 */
interface HierarchicalBeanFactory: BeanFactory {
}