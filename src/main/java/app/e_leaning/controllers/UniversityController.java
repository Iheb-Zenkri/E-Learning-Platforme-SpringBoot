package app.e_leaning.controllers;

import app.e_leaning.dtos.DepartmentDTO;
import app.e_leaning.dtos.UniversityDTO;
import app.e_leaning.models.University;
import app.e_leaning.services.UniversityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static app.e_leaning.dtos.UniversityDTO.*;

@RestController
@RequestMapping("/api/universities")
@Tag(name = "University Management", description = "Endpoints for managing universities")
@Validated
public class UniversityController {

    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @Operation(summary = "Create a new university")
    @PostMapping
    public ResponseEntity<UniversityDTO> createUniversity(@Valid @RequestBody UniversityDTO universityDTO) {
        University university = universityService.createUniversity(toUniversityEntity(universityDTO));
        return ResponseEntity.ok(toUniversityDTO(university));
    }

    @Operation(summary = "Get all universities")
    @GetMapping
    public ResponseEntity<Page<UniversityDTO>> getAllUniversities(Pageable pageable) {
        Page<UniversityDTO> universities = universityService.getAllUniversities(pageable);
        return ResponseEntity.ok(universities);
    }

    @Operation(summary = "Get a university by ID")
    @GetMapping("/{id}")
    public ResponseEntity<UniversityDTO> getUniversityById(@PathVariable Long id) {
        UniversityDTO university = universityService.getUniversityById(id);
        return ResponseEntity.ok(university);
    }

    @Operation(summary = "Update a university")
    @PutMapping("/{id}")
    public ResponseEntity<UniversityDTO> updateUniversity(@PathVariable Long id, @Valid @RequestBody UniversityDTO universityDTO) {
        UniversityDTO updatedUniversity = universityService.updateUniversity(id, universityDTO);
        return ResponseEntity.ok(updatedUniversity);
    }

    @Operation(summary = "Delete a university")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUniversity(@PathVariable Long id) {
        Boolean isDeleted = universityService.deleteUniversity(id);
        return ResponseEntity.ok(isDeleted);
    }

    @Operation(summary = "Get departments by university ID")
    @GetMapping("/departments/{id}")
    public ResponseEntity<Page<DepartmentDTO>> getDepartmentByUniversity(@PathVariable Long id, Pageable pageable) {
        Page<DepartmentDTO> departments = universityService.getDepartmentByUniversity(id, pageable);
        return ResponseEntity.ok(departments);
    }

    @Operation(summary = "Add a department to a university")
    @PostMapping("/departments/{universityId}/{departmentId}")
    public ResponseEntity<University> addSchoolToUniversity(@PathVariable Long universityId,@PathVariable Long departmentId) {
        University updatedUniversity = universityService.addDepartementToUniversity(universityId, departmentId);
        return ResponseEntity.ok(updatedUniversity);
    }

    @Operation(summary = "Remove a department from a university")
    @DeleteMapping("/{id}/department/{departmentId}")
    public ResponseEntity<University> removeDepartmentFromUniversity(@PathVariable Long id, @PathVariable Long departmentId) {
        University updatedUniversity = universityService.removeDepartementFromUniversity(id, departmentId);
        return ResponseEntity.ok(updatedUniversity);
    }


}
