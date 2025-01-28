package app.e_leaning.utils;

import app.e_leaning.dtos.DepartmentDTO;
import app.e_leaning.models.Department;

public class DepartmentMapper {

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
