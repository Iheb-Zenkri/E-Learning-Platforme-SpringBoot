package app.e_leaning.controllers;

import app.e_leaning.models.ImageFile;
import app.e_leaning.services.ImageService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImageFileController {

    private final ImageService imageService;

    public ImageFileController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/file/{imageId}")
    public ResponseEntity<FileSystemResource> getImageFileById(@PathVariable Long imageId) {
        ImageFile image = imageService.getImageById(imageId);
        FileSystemResource resource = imageService.getImageFileById(imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getMimeType()))
                .body(resource);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<ImageFile> getImageById(@PathVariable Long imageId) {
        ImageFile image = imageService.getImageById(imageId);
        return ResponseEntity.ok(image);
    }

    @PostMapping("/upload/{postId}")
    public ResponseEntity<Map<String, Object>> uploadImage(@PathVariable Long postId,
                                                           @RequestParam("file") MultipartFile file) {
        ImageFile image = imageService.saveFile(file, postId);
        return ResponseEntity.ok(Map.of(
                "message", "Image uploaded successfully",
                "image", image
        ));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Boolean> deleteImage(@PathVariable Long imageId) {
        boolean isDeleted = imageService.deleteImage(imageId);
        return ResponseEntity.ok(isDeleted);
    }
}
