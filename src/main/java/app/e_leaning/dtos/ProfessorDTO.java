package app.e_leaning.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProfessorDTO {
    private Long id ;
    private String email;
    private String firstName;
    private String lastName;
    private Timestamp lastLogin;
}
