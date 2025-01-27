package app.e_leaning.dtos;

import app.e_leaning.models.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private User.Role role;
    private boolean active;

}

