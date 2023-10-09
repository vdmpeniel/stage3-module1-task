package com.mjc.school.common.utils;

import com.mjc.school.common.implementation.PropertyLoader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class FileUtils {

    private final static PropertyLoader propertyLoader;

    static {
        try {
            propertyLoader = PropertyLoader.getInstance();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FileUtils() {}

    public static String readFile(String filename) throws IOException {
        Path filePath = getAbsolutePath(filename);
        return Files.readAllLines(filePath, StandardCharsets.UTF_8)
            .stream()
            .collect(Collectors.joining("/n"));
    }

    public static void writeFile(String filename, String content)throws IOException{
        Path filePath = getAbsolutePath(filename);
        Files.writeString(filePath, content, StandardCharsets.UTF_8);
    }

    public static Path getAbsolutePath(String filePath){
        File file = new File(filePath);
        return Path.of(file.getAbsolutePath());
    }
}
