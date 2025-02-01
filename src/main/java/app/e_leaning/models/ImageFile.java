package app.e_leaning.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class ImageFile extends BaseFileEntity {
    private Integer resolutionWidth;
    private Integer resolutionHeight;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
