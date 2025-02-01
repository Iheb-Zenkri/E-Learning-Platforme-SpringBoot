package app.e_leaning.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class VideoFile extends BaseFileEntity {
    private Long duration;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
