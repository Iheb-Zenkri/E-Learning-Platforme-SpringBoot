package app.e_leaning.dtos;

import app.e_leaning.models.University;
import lombok.Data;

import java.util.ArrayList;

@Data
public class UniversityDTO {
    private Long id;
    private String name;
    private String address;
    private int DepartmentCount = 0;

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

