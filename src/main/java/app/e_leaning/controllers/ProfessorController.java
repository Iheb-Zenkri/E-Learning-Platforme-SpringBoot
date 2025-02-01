package app.e_leaning.controllers;

import app.e_leaning.dtos.ClassesDTO;
import app.e_leaning.dtos.ProfessorDTO;
import app.e_leaning.models.Professor;
import app.e_leaning.services.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.e_leaning.dtos.ProfessorDTO.* ;

@RestController
@RequestMapping("/api/professors")
@Tag(name = "Professor Management", description = "Endpoints for managing professors")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @Operation(summary = "Get Professor by Id")
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> getProfessorById(@PathVariable Long id) {
        Professor professor = professorService.getProfessorById(id);
        return ResponseEntity.ok(toProfessorDTO(professor));
    }

    @Operation(summary = "Update Professor")
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> updateProfessor(@PathVariable Long id, @RequestBody ProfessorDTO professorDTO) {
        Professor updatedProfessor = professorService.updateProfessor(id, toProfessorEntity(professorDTO));
        return ResponseEntity.ok(toProfessorDTO(updatedProfessor));
    }

    @Operation(summary = "Delete Professor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProfessor(@PathVariable Long id) {
        boolean isDeleted = professorService.deleteProfessor(id);
        return ResponseEntity.ok(isDeleted);
    }

    @Operation(summary = "Get all Classes taught by specific Professor")
    @GetMapping("/{id}/classes")
    public ResponseEntity<List<ClassesDTO>> getClassesTaughtByProfessor(@PathVariable Long id) {
        List<ClassesDTO> classes = professorService.getClassesTaughtByProfessor(id).stream().map(ClassesDTO::toClassesDTO).toList();
        return ResponseEntity.ok(classes);
    }
}
