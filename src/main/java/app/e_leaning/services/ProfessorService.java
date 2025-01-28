package app.e_leaning.services;

import app.e_leaning.models.Classes;
import app.e_leaning.models.Professor;
import app.e_leaning.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;


    public Professor createProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    public Optional<Professor> getProfessorById(Long id) {
        return professorRepository.findById(id);
    }

    public Professor updateProfessor(Long id, Professor updatedProfessor) {
        return professorRepository.findById(id).map(professor -> {
            professor.setUser(updatedProfessor.getUser());
            return professorRepository.save(professor);
        }).orElseThrow(() -> new RuntimeException("Professor not found"));
    }

    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }

    public Professor assignProfessorToDepartmentAndSchool(Long professorId, Long departmentId, Long schoolId) {
        return professorRepository.findById(professorId).orElseThrow();
        // assign to class

    }

    public Page<Professor> getProfessorsByDepartment(Long departmentId, Pageable pageable) {
        return professorRepository.findAll(pageable);
    }


    public List<Classes> getClassesTaughtByProfessor(Long professorId) {
        Optional<Professor> professor = professorRepository.findById(professorId);
        if (professor.isPresent()) {
            return professor.get().getClasses();
        } else {
            throw new RuntimeException("Professor not found");
        }
    }
}
