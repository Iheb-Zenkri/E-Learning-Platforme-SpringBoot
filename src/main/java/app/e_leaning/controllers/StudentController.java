package app.e_leaning.controllers;

import app.e_leaning.dtos.ClassesDTO;
import app.e_leaning.dtos.StudentDTO;
import app.e_leaning.models.Student;
import app.e_leaning.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.e_leaning.dtos.StudentDTO.*;
@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Management", description = "Endpoints for managing students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Get student by ID")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(toStudentDTO(student));
    }

    @Operation(summary = "Update student details")
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO updatedStudent) {
        Student student = studentService.updateStudent(id, toStudentEntity(updatedStudent));
        return ResponseEntity.ok(toStudentDTO(student));
    }

    @Operation(summary = "Delete a student by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable Long id) {
        boolean deleted = studentService.deleteStudent(id);
        return ResponseEntity.ok(deleted);
    }

    @Operation(summary = "Get all classes for a student")
    @GetMapping("/{id}/classes")
    public ResponseEntity<List<ClassesDTO>> getStudentClasses(@PathVariable Long id) {
        List<ClassesDTO> classes = studentService.getStudentClasses(id).stream().map(ClassesDTO::toClassesDTO).toList();
        return ResponseEntity.ok(classes);
    }

}
