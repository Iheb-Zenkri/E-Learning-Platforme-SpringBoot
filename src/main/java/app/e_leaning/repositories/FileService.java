package app.e_leaning.repositories;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Object saveFile(MultipartFile file, Long postId);
}
