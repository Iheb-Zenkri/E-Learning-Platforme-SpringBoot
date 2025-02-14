package app.e_leaning.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mimeType;
    private String fileName;
    private String filePath;
    private Long size;
    private LocalDateTime uploadTime;
}

