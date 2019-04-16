package com.nb.user.util;

import org.dozer.Mapper;

/**
 * @description 对象复制工具类
 * @author: fly
 * @date: 2018/11/21 12:12
 */
public class DozerBeanMapperUtil {

    private DozerBeanMapperUtil(){};

    private final static Mapper  mapper = new org.dozer.DozerBeanMapper();

    public static <T> T dozerBeanMapper(Object obj1, Class<T> obj2){
        return mapper.map(obj1,obj2);
    }
}