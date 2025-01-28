package app.e_leaning.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "department")
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "universityId")
    @JsonIgnore
    private University university;

    @OneToMany(mappedBy = "department")
    private List<Classes> classes;

    // Getters and Setters
}
