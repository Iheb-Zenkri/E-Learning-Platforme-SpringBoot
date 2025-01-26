package app.e_leaning.services;

import app.e_leaning.models.Class;
import app.e_leaning.models.Department;
import app.e_leaning.models.Professor;
import app.e_leaning.models.School;
import app.e_leaning.repositories.DepartmentRepository;
import app.e_leaning.repositories.ProfessorRepository;
import app.e_leaning.repositories.SchoolRepository;
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

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    public Professor createProfessor(Professor professor, Long departmentId, Long schoolId) {
        // Fetch department and school
        Optional<Department> department = departmentRepository.findById(departmentId);
        Optional<School> school = schoolRepository.findById(schoolId);

        if (department.isPresent() && school.isPresent()) {
            professor.setDepartment((Department) department.get());
            professor.setSchool((School) school.get());
            return professorRepository.save(professor);
        } else {
            throw new RuntimeException("Department or School not found");
        }
    }

    public Optional<Professor> getProfessorById(Long id) {
        return professorRepository.findById(id);
    }

    public Professor updateProfessor(Long id, Professor updatedProfessor) {
        return professorRepository.findById(id).map(professor -> {
            professor.setUser(updatedProfessor.getUser());
            professor.setDepartment(updatedProfessor.getDepartment());
            professor.setSchool(updatedProfessor.getSchool());
            return professorRepository.save(professor);
        }).orElseThrow(() -> new RuntimeException("Professor not found"));
    }

    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }

    public Professor assignProfessorToDepartmentAndSchool(Long professorId, Long departmentId, Long schoolId) {
        Optional<Professor> professor = professorRepository.findById(professorId);
        Optional<Department> department = departmentRepository.findById(departmentId);
        Optional<School> school = schoolRepository.findById(schoolId);

        if (professor.isPresent() && department.isPresent() && school.isPresent()) {
            professor.get().setDepartment(department.get());
            professor.get().setSchool(school.get());
            return professorRepository.save(professor.get());
        } else {
            throw new RuntimeException("Professor, Department, or School not found");
        }
    }

    public Page<Professor> getProfessorsByDepartment(Long departmentId, Pageable pageable) {
        return professorRepository.findByDepartmentId(departmentId,pageable);
    }

    public Page<Professor> getProfessorsBySchool(Long schoolId,Pageable pageable) {
        return professorRepository.findBySchoolId(schoolId,pageable);
    }

    public List<Class> getClassesTaughtByProfessor(Long professorId) {
        Optional<Professor> professor = professorRepository.findById(professorId);
        if (professor.isPresent()) {
            return professor.get().getClasses();
        } else {
            throw new RuntimeException("Professor not found");
        }
    }
}
