package com.kandoka.springframework.beans;

/**
 * @Description bean内部的属性值
 *              用于bean实例化时的属性填充
 * @Author handong3
 * @Date 2022/2/16 15:08
 */
public class PropertyValue {

    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
