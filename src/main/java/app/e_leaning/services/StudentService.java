package app.e_leaning.services;

import app.e_leaning.exceptions.ObjectNotFoundException;
import app.e_leaning.models.Classes;
import app.e_leaning.models.Student;
import app.e_leaning.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;


    public Student createStudent(Student student, Long departmentId, Long schoolId) {
            return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id).map(student -> {
            student.setUser(updatedStudent.getUser());
            return studentRepository.save(student);
        }).orElseThrow(() -> new ObjectNotFoundException("Student not found"));
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student assignStudentToDepartmentAndSchool(Long studentId, Long departmentId, Long schoolId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            return studentRepository.save(student.get());
        } else {
            throw new RuntimeException("Student, Department, or School not found");
        }
    }

    public Student enrollStudentInClasses(Long studentId, List<Classes> classes) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            student.get().setClasses(classes);
            return studentRepository.save(student.get());
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    public Page<Student> getStudentsByDepartment(Long departmentId, Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

}
