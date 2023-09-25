package com.mjc.school.common.utils;

import com.mjc.school.common.implementation.utils.PropertyLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PropertyLoaderTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getInstance() throws IOException{
        PropertyLoader propertyLoader = PropertyLoader.getInstance();
        PropertyLoader propertyLoader2 = PropertyLoader.getInstance();
        assertEquals(propertyLoader, propertyLoader2);
    }


    @Test
    void getProperty() throws IOException {
        PropertyLoader propertyLoader = PropertyLoader.getInstance();
        String result = propertyLoader.getProperty("application.name");
        boolean endsWithElipsys = result.endsWith("...");
        assertTrue(endsWithElipsys);
    }
}