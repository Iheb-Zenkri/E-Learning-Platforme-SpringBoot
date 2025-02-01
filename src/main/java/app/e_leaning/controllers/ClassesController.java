package app.e_leaning.controllers;


import app.e_leaning.dtos.ClassesDTO;
import app.e_leaning.dtos.DepartmentDTO;
import app.e_leaning.dtos.ProfessorDTO;
import app.e_leaning.dtos.StudentDTO;
import app.e_leaning.models.Classes;
import app.e_leaning.models.Department;
import app.e_leaning.models.Professor;
import app.e_leaning.services.ClassesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static app.e_leaning.dtos.ClassesDTO.*;
import static app.e_leaning.dtos.DepartmentDTO.*;

@RestController
@RequestMapping("/api/classes")
@Tag(name = "Classes Management", description = "Endpoints for managing classes")
@Validated
public class ClassesController {

    private final ClassesService classesService;

    public ClassesController(ClassesService classesService) {
        this.classesService = classesService;
    }

    @Operation(summary = "Create a new class", description = "Add a new class to the system.")
    @PostMapping
    public ResponseEntity<ClassesDTO> createClass(@RequestBody ClassesDTO classesDTO) {
        Classes newClass = classesService.createClass(toClassesEntity(classesDTO));
        return ResponseEntity.ok(toClassesDTO(newClass));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClassesDTO> getClassById(@PathVariable Long id) {
        Classes foundClass = classesService.getClassById(id);
        return ResponseEntity.ok(toClassesDTO(foundClass));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassesDTO> updateClass(@PathVariable Long id,@Valid @RequestBody ClassesDTO classesDTO) {
        Classes updatedClass = classesService.updateClass(id, toClassesEntity(classesDTO));
        return ResponseEntity.ok(toClassesDTO(updatedClass));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteClass(@PathVariable Long id) {
        boolean isDeleted = classesService.deleteClass(id);
        return ResponseEntity.ok(isDeleted);
    }

    @GetMapping
    public ResponseEntity<Page<ClassesDTO>> getAllClasses(Pageable pageable) {
        Page<ClassesDTO> classes = classesService.getAllClasses(pageable).map(ClassesDTO::toClassesDTO);
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{classId}/students")
    public ResponseEntity<Page<StudentDTO>> getStudentsInClass(@PathVariable Long classId, Pageable pageable) {
        Page<StudentDTO> students = classesService.getStudentsInClass(classId, pageable).map(StudentDTO::toStudentDTO);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{classId}/professor")
    public ResponseEntity<ProfessorDTO> getProfessorOfClass(@PathVariable Long classId) {
        Professor professor = classesService.getProfessorOfClass(classId);
        return ResponseEntity.ok(ProfessorDTO.toProfessorDTO(professor));
    }
    
    @GetMapping("/{classId}/department")
    public ResponseEntity<DepartmentDTO> getDepartmentOfClass(@PathVariable Long classId){
        Department department = classesService.getDepartmentOfClass(classId);
        return ResponseEntity.ok(toDepartmentDTO(department));
    }

    @PostMapping("/{classId}/professor/{professorId}")
    public ResponseEntity<ClassesDTO> assignProfessorToClass(@PathVariable Long classId, @PathVariable Long professorId) {
        Classes updatedClass = classesService.assignProfessorToClass(classId, professorId);
        return ResponseEntity.ok(toClassesDTO(updatedClass));
    }

    @PostMapping("/{classId}/department/{departmentId}")
    public ResponseEntity<ClassesDTO> addClassToDepartment(@PathVariable Long classId, @PathVariable Long departmentId) {
        Classes updatedClass = classesService.addClassToDepartment(classId, departmentId);
        return ResponseEntity.ok(toClassesDTO(updatedClass));
    }

    @PostMapping("/{classId}/students/{studentId}")
    public ResponseEntity<Boolean> enrollStudentInClass(@PathVariable Long classId, @PathVariable Long studentId) {
        boolean enrolled = classesService.enrollStudentInClass(classId, studentId);
        return ResponseEntity.ok(enrolled);
    }

    @DeleteMapping("/{classId}/students/{studentId}")
    public ResponseEntity<Boolean> removeStudentFromClass(@PathVariable Long classId, @PathVariable Long studentId) {
        boolean removed = classesService.removeStudentFromClass(classId, studentId);
        return ResponseEntity.ok(removed);
    }
}

