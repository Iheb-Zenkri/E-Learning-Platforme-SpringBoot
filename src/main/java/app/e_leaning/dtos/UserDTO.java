package app.e_leaning.dtos;

import app.e_leaning.models.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Timestamp lastLogin;
    private User.Role role;
}

