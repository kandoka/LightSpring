package com.kandoka.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description bean内部的属性集合
 *              用于bean实例化时的属性填充
 *              因为属性可能会有很多，所以需要定义这样一个集合包装下。
 * @Author handong3
 * @Date 2022/2/16 15:12
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv){
        this.propertyValueList.add(pv);
    }

    public PropertyValue[] getPropertyValues(){
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName){
        for(PropertyValue pv: this.propertyValueList){
            if(pv.getName().equals(propertyName)){
                return pv;
            }
        }
        return null;
    }
}
