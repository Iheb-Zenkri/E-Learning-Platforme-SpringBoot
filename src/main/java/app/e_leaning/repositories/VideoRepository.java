package app.e_leaning.repositories;

import app.e_leaning.models.VideoFile;
import app.e_leaning.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<VideoFile, Long> {
    List<VideoFile> findByPost(Post post);
}
