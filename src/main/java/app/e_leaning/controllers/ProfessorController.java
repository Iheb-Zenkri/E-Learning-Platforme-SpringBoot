package app.e_leaning.controllers;

import app.e_leaning.dtos.ClassesDTO;
import app.e_leaning.dtos.ProfessorDTO;
import app.e_leaning.models.Professor;
import app.e_leaning.services.ProfessorService;
import app.e_leaning.utils.ClassesMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.e_leaning.utils.ProfessorMapper.toProfessorDTO;
import static app.e_leaning.utils.ProfessorMapper.toProfessorEntity;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> getProfessorById(@PathVariable Long id) {
        Professor professor = professorService.getProfessorById(id);
        return ResponseEntity.ok(toProfessorDTO(professor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> updateProfessor(@PathVariable Long id, @RequestBody ProfessorDTO professorDTO) {
        Professor updatedProfessor = professorService.updateProfessor(id, toProfessorEntity(professorDTO));
        return ResponseEntity.ok(toProfessorDTO(updatedProfessor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProfessor(@PathVariable Long id) {
        boolean isDeleted = professorService.deleteProfessor(id);
        return ResponseEntity.ok(isDeleted);
    }

    @GetMapping("/{id}/classes")
    public ResponseEntity<List<ClassesDTO>> getClassesTaughtByProfessor(@PathVariable Long id) {
        List<ClassesDTO> classes = professorService.getClassesTaughtByProfessor(id).stream().map(ClassesMapper::toClassesDTO).toList();
        return ResponseEntity.ok(classes);
    }
}
