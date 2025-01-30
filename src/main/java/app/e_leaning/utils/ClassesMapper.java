package app.e_leaning.utils;

import app.e_leaning.dtos.ClassesDTO;
import app.e_leaning.models.Classes;

public class ClassesMapper {
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
