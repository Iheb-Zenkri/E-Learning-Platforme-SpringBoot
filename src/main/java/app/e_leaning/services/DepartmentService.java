package app.e_leaning.services;

import app.e_leaning.exceptions.ObjectNotFoundException;
import app.e_leaning.models.Department;
import app.e_leaning.repositories.DepartmentRepository;
import app.e_leaning.repositories.ProfessorRepository;
import app.e_leaning.repositories.StudentRepository;
import app.e_leaning.repositories.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UniversityRepository universityRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Department with id "+id+" not found."));
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        return departmentRepository.findById(id).map(department -> {
            department.setName(updatedDepartment.getName());
            return departmentRepository.save(department);
        }).orElseThrow(() -> new RuntimeException("Department not found"));
    }

    public boolean deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
        return !departmentRepository.existsById(id);
    }

    public Page<Department> getAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }


}
