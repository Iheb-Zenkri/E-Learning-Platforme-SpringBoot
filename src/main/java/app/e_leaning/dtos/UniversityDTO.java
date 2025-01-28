package app.e_leaning.dtos;

import lombok.Data;

@Data
public class UniversityDTO {
    private Long id;
    private String name;
    private String address;
    private int DepartmentCount = 0;
}

