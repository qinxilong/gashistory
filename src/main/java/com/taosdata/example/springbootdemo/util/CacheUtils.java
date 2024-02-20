package com.taosdata.example.springbootdemo.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtils {
    private static final Map<String, Object> cache = new ConcurrentHashMap<>();

    // 向缓存中写入数据
    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    // 从缓存中获取数据
    public static Object get(String key) {
        return cache.get(key);
    }

    // 从缓存中移除数据
    public static void remove(String key) {
        cache.remove(key);
    }

    // 判断缓存中是否存在指定的键
    public static boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    // 清空缓存
    public static void clear() {
        cache.clear();
    }

    public static Map<String, Object> getCache() {
        return cache;
    }

}