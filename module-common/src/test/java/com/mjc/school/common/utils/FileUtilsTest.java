package com.mjc.school.common.utils;

import com.mjc.school.common.implementation.utils.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void readFile() throws IOException {

        String result = FileUtils.readFile( "src/test/resources/FileTest1.txt");
        assertNotEquals("", result);
    }

    @Test
    void writeFile() throws IOException{
        String result1 = FileUtils.readFile( "src/test/resources/FileTest1.txt");
        FileUtils.writeFile("src/test/resources/FileTest1.txt", "abcd");

        String result2 = FileUtils.readFile( "src/test/resources/FileTest1.txt");
        assertEquals("abcd", result2);

        FileUtils.writeFile("src/test/resources/FileTest1.txt", result1);
    }

    @Test
    void getAbsolutePath() {
        boolean endsCorrectly = FileUtils.getAbsolutePath("test/resources/FileTest1.txt").endsWith("test/resources/FileTest1.txt");
        assertTrue(endsCorrectly);
    }
}