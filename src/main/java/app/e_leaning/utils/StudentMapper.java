package app.e_leaning.utils;

import app.e_leaning.dtos.StudentDTO;
import app.e_leaning.models.Student;

public class StudentMapper {
    public static StudentDTO toStudentDTO(Student student){
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setEmail(student.getUser().getEmail());
        dto.setFirstName(student.getUser().getFirstName());
        dto.setLastName(student.getUser().getLastName());
        return dto;
    }

    public static Student toStudentEntity(StudentDTO dto){
        Student student = new Student();
        student.setId(dto.getId());
        student.getUser().setFirstName(dto.getFirstName());
        student.getUser().setLastName(dto.getLastName());
        student.getUser().setEmail(dto.getEmail());
        return student ;
    }
}
