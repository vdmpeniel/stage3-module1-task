package com.mjc.school.common.utils;

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
        synchronized(PropertyLoader.class){
            if(Objects.isNull(instance)){
                instance = new PropertyLoader();
            }
            return instance;
        }
    }

    public void loadProperties() throws IOException{
        try (InputStream inputStream = PropertyLoader.class.getResourceAsStream("/application.properties")) {
            properties.load(inputStream);

        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
            throw e;
        }
    }

    public String getProperty(String key) throws IOException{
        return getInstance().getProperty(key);
    }
}
