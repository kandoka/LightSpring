package com.kandoka.springframework.core.io;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/2/16 16:22
 */
public interface ResourceLoader {
    /**
     * Pseudo URL prefix for loading from the class path: "classpath:"
     */

    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);
}
