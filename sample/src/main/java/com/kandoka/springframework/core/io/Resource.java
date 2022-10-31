package com.kandoka.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description 资源加载器接口，负责加载各种配置文件。资源加载器的实现中包括了，ClassPath、系统文件、云配置文件
 * @Author handong3
 * @Date 2022/2/16 16:22
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
}
