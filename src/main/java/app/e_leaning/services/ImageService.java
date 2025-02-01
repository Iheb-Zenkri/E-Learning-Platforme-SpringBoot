package app.e_leaning.services;

import app.e_leaning.exceptions.ObjectNotFoundException;
import app.e_leaning.models.ImageFile;
import app.e_leaning.repositories.ImageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Page<ImageFile> getAllImages(Pageable pageable) {
        return imageRepository.findAll(pageable);
    }

    public ImageFile getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Image not found"));
    }

    @Transactional
    public ImageFile saveImage(ImageFile imageFile) {
        return imageRepository.save(imageFile);
    }

    @Transactional
    public boolean deleteImage(Long id) {
        imageRepository.deleteById(id);
        return !imageRepository.existsById(id);
    }
}

