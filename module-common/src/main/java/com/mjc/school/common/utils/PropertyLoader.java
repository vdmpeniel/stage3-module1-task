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

    public static PropertyLoader getInstance(){
        try {
            synchronized (PropertyLoader.class) {
                if (Objects.isNull(instance)) {
                    instance = new PropertyLoader();
                }
                return instance;
            }

        } catch(IOException ioe){
            System.out.println("Error: " + ioe.getMessage());
            throw new RuntimeException(ioe);
        }
    }

    private void loadProperties(){
        try (InputStream inputStream = PropertyLoader.class.getResourceAsStream("/application.properties")) {
            properties.load(inputStream);

        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key){
        return getInstance().properties.getProperty(key);
    }
}
