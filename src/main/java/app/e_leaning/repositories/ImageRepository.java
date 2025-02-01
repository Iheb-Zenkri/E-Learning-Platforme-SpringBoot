package app.e_leaning.repositories;

import app.e_leaning.models.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageFile, Long> {}
