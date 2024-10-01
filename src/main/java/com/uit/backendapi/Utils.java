package com.uit.backendapi;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    public static String saveFile(MultipartFile file, String fileName, String folder) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        Path filePath = Paths.get(folder, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        return filePath.toString();
    }

    public static void copyNonNullProperties(Object src, Object target) {
        Field[] fields = src.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(src);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
