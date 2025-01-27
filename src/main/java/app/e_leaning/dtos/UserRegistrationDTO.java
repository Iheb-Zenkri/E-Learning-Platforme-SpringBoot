package app.e_leaning.dtos;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
