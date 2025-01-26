package app.e_leaning.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "class")
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "departmentId", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "professorId", nullable = false)
    private Professor professor;

    @ManyToMany(mappedBy = "classes")
    private List<Student> students;

    private String semester;
}
