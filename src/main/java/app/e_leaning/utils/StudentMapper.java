package app.e_leaning.utils;

import app.e_leaning.dtos.StudentDTO;
import app.e_leaning.models.Student;
import app.e_leaning.models.User;

public class StudentMapper {
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
