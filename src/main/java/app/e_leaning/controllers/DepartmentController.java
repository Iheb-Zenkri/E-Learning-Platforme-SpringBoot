package app.e_leaning.controllers;

import app.e_leaning.dtos.DepartmentDTO;
import app.e_leaning.models.Department;
import app.e_leaning.services.DepartmentService;
import app.e_leaning.utils.DepartmentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static app.e_leaning.utils.DepartmentMapper.toDepartmentDTO;
import static app.e_leaning.utils.DepartmentMapper.toDepartmentEntity;

@RestController
@RequestMapping("/api/departments")
@Tag(name = "Department Management", description = "Endpoints for managing departments")
@Validated
public class DepartmentController {

    private final DepartmentService departmentService ;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(summary = "Create a new department", description = "Add a new department to the system.")
    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO){
        Department department = departmentService.createDepartment(toDepartmentEntity(departmentDTO));
        return ResponseEntity.ok(toDepartmentDTO(department));
    }

    @Operation(summary = "Get all universities", description = "Retrieve a paginated list of all universities.")
    @GetMapping
    public ResponseEntity<Page<DepartmentDTO>> getAllDepartments(Pageable pageable){
        Page<Department> departmentDTOS = departmentService.getAllDepartments(pageable);
        return ResponseEntity.ok(departmentDTOS.map(DepartmentMapper::toDepartmentDTO));
    }

    @Operation(summary = "Get a department by ID", description = "Retrieve a department by its unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id){
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(toDepartmentDTO(department));
    }

    @Operation(summary = "Update a department", description = "Update the details of an existing department.")
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id,@Valid @RequestBody DepartmentDTO departmentDTO){
        Department updatedDepartment = departmentService.updateDepartment(id,toDepartmentEntity(departmentDTO));
        return ResponseEntity.ok(toDepartmentDTO(updatedDepartment));
    }

    @Operation(summary = "Delete a department", description = "Remove a department from the system by its unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable Long id){
        boolean isDeleted = departmentService.deleteDepartment(id);
        return ResponseEntity.ok(isDeleted) ;
    }
}
