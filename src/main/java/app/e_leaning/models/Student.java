package app.e_leaning.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "student_class",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "classId")
    )
    private List<Classes> classes;

}
