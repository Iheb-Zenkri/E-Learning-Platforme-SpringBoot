package app.e_leaning.dtos;

import app.e_leaning.models.Department;
import lombok.Data;

@Data
public class DepartmentDTO {
    private Long id;
    private String name;

    public static DepartmentDTO toDepartmentDTO(Department department){
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        return dto ;
    }

    public static Department toDepartmentEntity(DepartmentDTO department){
        Department dto = new Department();
        dto.setId(department.getId());
        dto.setName(department.getName());
        return dto ;
    }
}
