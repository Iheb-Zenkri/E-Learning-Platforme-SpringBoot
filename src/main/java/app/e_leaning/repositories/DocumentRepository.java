package app.e_leaning.repositories;

import app.e_leaning.models.DocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<DocumentFile, Long> {}
