package app.e_leaning.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "university")
@Data
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "University name is required")
    @Size(max = 255)
    private String name;

    @NotBlank(message = "University address is required")
    @Size(max = 255)
    private String address;

    @OneToMany
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private List<Administration> administrations;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<Department> departments;

}
