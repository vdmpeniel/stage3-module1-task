package com.mjc.school.common.implementation.utils;

import java.util.HashMap;
import java.util.Map;

public class IdGeneratorUtils {
    private volatile static Map<Class<?>, Long> nextIdMap = new HashMap<>();

    private IdGeneratorUtils(){}

    public synchronized static void reset(Class<?> clazz){
        nextIdMap.put(clazz, 1L);
    }

    public synchronized static long generateId(Class<?> clazz) {
        if (!nextIdMap.containsKey(clazz)) {
            nextIdMap.put(clazz, 1L);
        }
        Long currentValue = nextIdMap.get(clazz);
        nextIdMap.put(clazz, nextIdMap.get(clazz) + 1);
        return currentValue;
    }
}