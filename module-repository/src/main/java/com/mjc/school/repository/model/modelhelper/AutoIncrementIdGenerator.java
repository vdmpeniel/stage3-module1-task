package com.mjc.school.repository.model.modelhelper;

import java.util.HashMap;
import java.util.Map;

public class AutoIncrementIdGenerator{
    private volatile static Map<Class<?>, Long> nextIdMap = new HashMap<>();

    private AutoIncrementIdGenerator(Class<?> clazz){}

    public synchronized static void reset(Class<?> clazz){
        nextIdMap.put(clazz, 0L);
    }

    public synchronized static long generateId(Class<?> clazz) {
        if (!nextIdMap.containsKey(clazz)) {
            nextIdMap.put(clazz, 0L);
        }
        Long currentValue = nextIdMap.get(clazz);
        nextIdMap.put(clazz, nextIdMap.get(clazz) + 1);
        return currentValue;
    }
}