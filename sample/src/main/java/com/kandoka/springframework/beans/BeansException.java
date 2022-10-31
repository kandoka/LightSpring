package com.kandoka.springframework.beans;

/**
 *
 * @Description TODO
 * @Author handong3
 * @Date 2022/2/15 15:50
 *
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
