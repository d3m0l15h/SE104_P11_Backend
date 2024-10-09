package com.uit.backendapi;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

    public static void copyNonNullProperties(Object source, Object destination, String... ignoreProperties) {
        Set<String> ignorePropertiesSet = getNullPropertyNames(source);
        if (ignoreProperties != null) {
            Collections.addAll(ignorePropertiesSet, ignoreProperties);
        }
        BeanUtils.copyProperties(source, destination, ignorePropertiesSet.toArray(new String[0]));
    }

    public static Set<String> getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            // Check if value of this property is null then add it to the collection
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        return emptyNames;
    }
}
