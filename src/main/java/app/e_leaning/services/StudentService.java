package app.e_leaning.services;

import app.e_leaning.models.Department;
import app.e_leaning.models.School;
import app.e_leaning.models.Student;
import app.e_leaning.models.Class;
import app.e_leaning.repositories.StudentRepository;
import app.e_leaning.repositories.DepartmentRepository;
import app.e_leaning.repositories.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    public Student createStudent(Student student, Long departmentId, Long schoolId) {
        // Fetch department and school
        Optional<Department> department = departmentRepository.findById(departmentId);
        Optional<School> school = schoolRepository.findById(schoolId);

        if (department.isPresent() && school.isPresent()) {
            student.setDepartment((Department) department.get());
            student.setSchool((School) school.get());
            return studentRepository.save(student);
        } else {
            throw new RuntimeException("Department or School not found");
        }
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id).map(student -> {
            student.setUser(updatedStudent.getUser());
            student.setDepartment(updatedStudent.getDepartment());
            student.setSchool(updatedStudent.getSchool());
            return studentRepository.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student assignStudentToDepartmentAndSchool(Long studentId, Long departmentId, Long schoolId) {
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Department> department = departmentRepository.findById(departmentId);
        Optional<School> school = schoolRepository.findById(schoolId);

        if (student.isPresent() && department.isPresent() && school.isPresent()) {
            student.get().setDepartment(department.get());
            student.get().setSchool(school.get());
            return studentRepository.save(student.get());
        } else {
            throw new RuntimeException("Student, Department, or School not found");
        }
    }

    public Student enrollStudentInClasses(Long studentId, List<Class> classes) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            student.get().setClasses(classes);
            return studentRepository.save(student.get());
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    public List<Student> getStudentsByDepartment(Long departmentId) {
        return studentRepository.findByDepartmentId(departmentId);
    }

    // Get all students by school
    public List<Student> getStudentsBySchool(Long schoolId) {
        return studentRepository.findBySchoolId(schoolId);
    }
}
