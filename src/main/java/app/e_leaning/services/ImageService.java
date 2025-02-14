package app.e_leaning.services;

import app.e_leaning.dtos.FileStoredDto;
import app.e_leaning.exceptions.ObjectNotFoundException;
import app.e_leaning.models.ImageFile;
import app.e_leaning.models.Post;
import app.e_leaning.repositories.FileService;
import app.e_leaning.repositories.ImageRepository;
import app.e_leaning.repositories.PostRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Service
public class ImageService implements FileService {
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;
    private final FileStorageService fileStorageService;

    public ImageService(ImageRepository imageRepository, PostRepository postRepository, FileStorageService fileStorageService) {
        this.imageRepository = imageRepository;
        this.postRepository = postRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    @Transactional
    public ImageFile saveFile(MultipartFile imageFile, Long postId) {
        try {
            validateImageFile(imageFile);

            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new ObjectNotFoundException("Post not found"));

            FileStoredDto fileStoredDto = fileStorageService.storeFile(imageFile);

            int[] dimensions = getImageDimensions(imageFile);

            ImageFile image = new ImageFile();
            image.setFileName(fileStoredDto.getFileName());
            image.setFilePath("uploads/" + fileStoredDto.getFileName());
            image.setSize(imageFile.getSize());
            image.setMimeType(fileStoredDto.getMimeType());
            image.setResolutionWidth(dimensions[0]);
            image.setResolutionHeight(dimensions[1]);
            image.setPost(post);

            return imageRepository.save(image);
        } catch (Exception e) {
            throw new RuntimeException("Failed to store image: " + e.getMessage());
        }
    }

    public FileSystemResource getImageFileById(Long imageId) {
        ImageFile image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ObjectNotFoundException("Image not found"));
        try {
            return fileStorageService.getFile(image.getFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImageFile getImageById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new ObjectNotFoundException("Image not found"));
    }

    public boolean deleteImage(Long imageId) {
        ImageFile image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ObjectNotFoundException("Image not found"));

        try {
            boolean deleted = fileStorageService.deleteFile(image.getFileName());
            if (deleted) {
                imageRepository.deleteById(imageId);
            }
            return !imageRepository.existsById(imageId);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting image file");
        }
    }

    private void validateImageFile(MultipartFile file) {
        List<String> allowedTypes = List.of("image/jpeg", "image/png", "image/gif");

        if (!allowedTypes.contains(file.getContentType())) {
            throw new IllegalArgumentException("Invalid image type");
        }
    }

    private int[] getImageDimensions(MultipartFile file) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            return new int[]{image.getWidth(), image.getHeight()};
        } catch (IOException e) {
            throw new RuntimeException("Failed to get image dimensions");
        }
    }
}
