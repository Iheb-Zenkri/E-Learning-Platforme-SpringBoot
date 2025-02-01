package app.e_leaning.dtos;

import app.e_leaning.models.Classes;
import lombok.Data;

@Data
public class ClassesDTO {
    private Long id;
    private String title;
    private String subject;
    private String semester;

    public static ClassesDTO toClassesDTO(Classes classes) {
        ClassesDTO dto = new ClassesDTO();
        dto.setId(classes.getId());
        dto.setTitle(classes.getTitle());
        dto.setSubject(classes.getSubject());
        dto.setSemester(classes.getSemester());
        return dto;
    }

    public static Classes toClassesEntity(ClassesDTO classesDTO) {
        Classes classes = new Classes();
        classes.setId(classesDTO.getId());
        classes.setTitle(classesDTO.getTitle());
        classes.setSubject(classesDTO.getSubject());
        classes.setSemester(classesDTO.getSemester());
        return classes;
    }
}
