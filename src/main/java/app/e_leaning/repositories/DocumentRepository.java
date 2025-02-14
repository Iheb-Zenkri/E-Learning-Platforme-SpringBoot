package app.e_leaning.repositories;

import app.e_leaning.models.DocumentFile;
import app.e_leaning.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentFile, Long> {
    List<DocumentFile> findByPost(Post post);
}
