package app.e_leaning.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "User")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotEmpty(message = "Firstname is required")
    private String firstName;
    private String lastName;
    private Timestamp lastLogin;

    private boolean isActive;

    public enum Role {
        SUPER_ADMIN,
        UNIVERSITY_ADMIN,
        Professor,
        Student,
    }
}

