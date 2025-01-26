package app.e_leaning.services;

import app.e_leaning.models.School;
import app.e_leaning.models.Department;
import app.e_leaning.repositories.SchoolRepository;
import app.e_leaning.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public School createSchool(School school) {
        return schoolRepository.save(school);
    }

    public Optional<School> getSchoolById(Long id) {
        return schoolRepository.findById(id);
    }

    public School updateSchool(Long id, School updatedSchool) {
        return schoolRepository.findById(id).map(school -> {
            school.setName(updatedSchool.getName());
            return schoolRepository.save(school);
        }).orElseThrow(() -> new RuntimeException("School not found"));
    }

    public void deleteSchool(Long id) {
        schoolRepository.deleteById(id);
    }

    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    public List<Department> getDepartmentsBySchool(Long schoolId) {
        return departmentRepository.findBySchoolId(schoolId);
    }
}
