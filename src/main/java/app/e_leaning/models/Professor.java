package app.e_leaning.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "departmentId", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "schoolId", nullable = false)
    private School school;

    @OneToMany(mappedBy = "professor")
    private List<Classes> classes;
}
