package com.kandoka.springframework.context;

import java.util.EventObject;

/**
 * @Description 定义应用的事件类
 * @Author handong3
 * @Date 2022/3/25 14:58
 */
public abstract class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
