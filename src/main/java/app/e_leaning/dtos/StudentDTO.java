package app.e_leaning.dtos;

import lombok.Data;

@Data
public class StudentDTO {
    private Long id ;
    private String email;
    private String firstName;
    private String lastName;
}
