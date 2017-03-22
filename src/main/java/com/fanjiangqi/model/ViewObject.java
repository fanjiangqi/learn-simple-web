package com.fanjiangqi.model;

import org.apache.commons.collections.map.HashedMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fanjiangqi on 2017/3/21.
 * 主要给视图展示用
 */
public class ViewObject {
    private Map<String, Object> objs = new HashMap<>();

    public void set(String key, Object value){
        objs.put(key, value);
    }
    public Object get(String key){
        return objs.get(key);
    }
}
