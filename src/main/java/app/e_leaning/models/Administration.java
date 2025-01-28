package app.e_leaning.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "administration")
public class Administration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "universityId", nullable = false)
    private University university;
}
