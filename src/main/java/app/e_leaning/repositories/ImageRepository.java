package app.e_leaning.repositories;

import app.e_leaning.models.ImageFile;
import app.e_leaning.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageFile, Long> {
    List<ImageFile> findByPost(Post post);
}
