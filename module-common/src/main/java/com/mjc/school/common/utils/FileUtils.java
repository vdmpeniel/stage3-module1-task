package com.mjc.school.common.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class FileUtils {

    private static PropertyLoader propertyLoader;
    static{
        try {
            propertyLoader = PropertyLoader.getInstance();

        } catch(IOException ioe) {
            throw new RuntimeException(ioe.getMessage());
        }
    }

    private FileUtils(){}

    public static String readFile(String filename) throws IOException {
        Path filePath = Path.of(filename);
        return Files.readAllLines(filePath, StandardCharsets.UTF_8)
                .stream()
                .collect(Collectors.joining("/n"));
    }

    public static void writeFile(String filename, String content)throws IOException{
        Path filePath = Path.of(filename);
        Files.writeString(filePath, content, StandardCharsets.UTF_8);
    }
}
