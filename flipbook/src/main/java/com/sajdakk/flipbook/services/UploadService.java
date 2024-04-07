package com.sajdakk.flipbook.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class UploadService {
    @Value("${file.uploads-dir}")
    private String uploadsDir;

    public String uploadFile(byte[] file, String extension) {
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "." + extension;
        try (FileOutputStream fos = new FileOutputStream(uploadsDir + fileName)) {
            fos.write(file);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file", e);
        }

        return fileName;
    }

    public byte[] getFile(String fileName) {
        try {
            return java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(uploadsDir + fileName));
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            java.nio.file.Files.delete(java.nio.file.Paths.get(uploadsDir + fileName));
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file", e);
        }
    }
}
