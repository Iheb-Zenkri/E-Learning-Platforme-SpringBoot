package app.e_leaning.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<ImageFile> images;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<VideoFile> videos;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<DocumentFile> documents;

    private LocalDateTime createdAt = LocalDateTime.now();
}
