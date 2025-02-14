package app.e_leaning.services;

import app.e_leaning.dtos.FileStoredDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads/";

    public FileStoredDto storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot store empty file");
        }

        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directory if not exists
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path destinationPath = Paths.get(UPLOAD_DIR, fileName);
        Files.copy(file.getInputStream(), destinationPath);
        String mimeType = Files.probeContentType(destinationPath);

        return new FileStoredDto(fileName,mimeType);
    }

    public FileSystemResource getFile(String fileName) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        String mimeType = Files.probeContentType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        return new FileSystemResource(filePath.toFile());
    }

    public boolean deleteFile(String fileName) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        return Files.deleteIfExists(filePath);
    }
}
