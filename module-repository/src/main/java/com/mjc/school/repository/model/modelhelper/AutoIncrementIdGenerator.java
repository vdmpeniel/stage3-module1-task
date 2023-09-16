package com.mjc.school.repository.model.modelhelper;

import java.util.HashMap;
import java.util.Map;

public class AutoIncrementIdGenerator implements IdGenerator{
    private volatile static Map<Class<?>, Long> nextIdMap = new HashMap<>();

    public AutoIncrementIdGenerator(Class<?> clazz){
        if (!nextIdMap.containsKey(clazz)) {
            nextIdMap.put(clazz, 0L);
        } else {
            nextIdMap.put(clazz, nextIdMap.get(clazz) + 1);
        }
    }

    public synchronized long generateId(Class<?> clazz) {
        long currentId = nextIdMap.get(clazz);
        nextIdMap.put(clazz, currentId);
        return currentId++;
    }
}