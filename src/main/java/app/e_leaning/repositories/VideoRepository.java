package app.e_leaning.repositories;

import app.e_leaning.models.VideoFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoFile, Long> {}

