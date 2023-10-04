package com.mjc.school.common.implementation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertyLoader {
    private final Properties properties = new Properties();
    private volatile static PropertyLoader instance;

    private PropertyLoader() throws IOException{
        loadProperties();
    }

    public static PropertyLoader getInstance() throws IOException{

        synchronized (PropertyLoader.class) {
            if (Objects.isNull(instance)) {
                instance = new PropertyLoader();
            }
            return instance;
        }

    }

    private void loadProperties() throws IOException{
        try (InputStream inputStream = PropertyLoader.class.getResourceAsStream("/application.properties")) {
            properties.load(inputStream);
        }
    }

    public String getProperty(String key) throws IOException{
        return getInstance().properties.getProperty(key);
    }
}
