package com.kandoka.springframework.beans.factory;

/**
 * @Description 感知接口类，实现该接口的类的实例可以被容器感知
 *              在 Spring 中有特别多类似这样的标记接口的设计方式，它们的存在就像是一种标
 *              签一样，可以方便统一摘取出属于此类接口的实现类，通常会有 instanceof 一起
 *              判断使用。
 * @Author handong3
 * @Date 2022/2/24 14:39
 */
public interface Aware {
}
