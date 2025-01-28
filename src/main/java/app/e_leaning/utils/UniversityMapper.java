package app.e_leaning.utils;

import app.e_leaning.dtos.UniversityDTO;
import app.e_leaning.models.University;

import java.util.ArrayList;

public class UniversityMapper {
    public static UniversityDTO toUniversityDTO(University university) {
        UniversityDTO dto = new UniversityDTO();
        dto.setId(university.getId());
        dto.setName(university.getName());
        dto.setAddress(university.getAddress());
        dto.setDepartmentCount(university.getDepartments().isEmpty() ? 0 : university.getDepartments().size() );
        return dto;
    }

    public static University toUniversityEntity(UniversityDTO dto) {
        University university = new University();
        university.setId(dto.getId());
        university.setName(dto.getName());
        university.setAddress(dto.getAddress());
        university.setAdministrations(new ArrayList<>());
        university.setDepartments(new ArrayList<>());
        return university;
    }

}
