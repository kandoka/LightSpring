package com.kandoka.springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Description TODO
 * @Author handong3
 * @Date 2022/2/16 16:22
 */
public class DefaultResourceLoader implements ResourceLoader {
    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
        //如果已classpath开头，说明是需要从java类读取配置
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }
        else {
            try {
                //尝试从网络地址读取配置
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                //如果发生异常，说明是剩下的情况，从文件系统读取配置
                return new FileSystemResource(location);
            }
        }
    }
}
