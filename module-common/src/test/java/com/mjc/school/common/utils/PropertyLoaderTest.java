package com.mjc.school.common.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyLoaderTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getInstance() {
        PropertyLoader propertyLoader = PropertyLoader.getInstance();
        PropertyLoader propertyLoader2 = PropertyLoader.getInstance();
        assertEquals(propertyLoader, propertyLoader2);
    }


    @Test
    void getProperty() {
        PropertyLoader propertyLoader = PropertyLoader.getInstance();
        String result = propertyLoader.getProperty("application.name");
        boolean endsWithElipsys = result.endsWith("...");
        assertTrue(endsWithElipsys);
    }
}