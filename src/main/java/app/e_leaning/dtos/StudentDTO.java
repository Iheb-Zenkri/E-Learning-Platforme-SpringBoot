package app.e_leaning.dtos;

import app.e_leaning.models.Student;
import app.e_leaning.models.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class StudentDTO {
    private Long id ;
    private String email;
    private String firstName;
    private String lastName;
    private Timestamp lastLogin;
    public static StudentDTO toStudentDTO(Student student){
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setEmail(student.getUser().getEmail());
        dto.setFirstName(student.getUser().getFirstName());
        dto.setLastName(student.getUser().getLastName());
        dto.setLastLogin(student.getUser().getLastLogin());
        return dto;
    }

    public static Student toStudentEntity(StudentDTO dto){
        Student student = new Student();
        student.setId(dto.getId());
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        student.setUser(user);
        return student ;
    }
}
