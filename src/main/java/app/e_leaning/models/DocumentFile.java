package app.e_leaning.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class DocumentFile extends BaseFileEntity {
    private Integer pageCount;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}

