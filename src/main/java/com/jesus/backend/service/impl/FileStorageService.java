package com.jesus.backend.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveImage(MultipartFile file) {
        try {
            // Validar vacío
            if (file.isEmpty()) {
                throw new RuntimeException("Image is empty.");
            }

            // Crear nombre único
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Ruta completa
            Path path = Paths.get(uploadDir, fileName);

            // Crear carpeta si no existe
            Files.createDirectories(path.getParent());

            // Guardar archivo
            Files.write(path, file.getBytes());

            return fileName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getImage(String fileName) {
        try {
            Path path = Paths.get(uploadDir, fileName);

            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Image Not Found.");
        }
    }

    public Path getImagePath(String fileName) {
        return Paths.get(uploadDir, fileName);
    }
}
