package app.e_leaning.services;

import app.e_leaning.models.Class;
import app.e_leaning.models.Department;
import app.e_leaning.models.Professor;
import app.e_leaning.models.Student;
import app.e_leaning.repositories.ClassRepository;
import app.e_leaning.repositories.DepartmentRepository;
import app.e_leaning.repositories.ProfessorRepository;
import app.e_leaning.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository ;

    @Autowired
    private ProfessorRepository professorRepository ;

    public Class createClass(Class newClass) {
        return classRepository.save(newClass);
    }

    public Optional<Class> getClassById(Long id) {
        return classRepository.findById(id);
    }

    public Class updateClass(Long id, Class updatedClass) {
        return classRepository.findById(id).map(existingClass -> {
            existingClass.setTitle(updatedClass.getTitle());
            existingClass.setDescription(updatedClass.getDescription());
            existingClass.setProfessor(updatedClass.getProfessor());
            existingClass.setSemester(updatedClass.getSemester());
            existingClass.setDepartment(updatedClass.getDepartment());
            return classRepository.save(existingClass);
        }).orElseThrow(() -> new RuntimeException("Class not found"));
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }

    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }

    public List<Student> getStudentsInClass(Long classId) {
        return studentRepository.findByClassId(classId);
    }

    public Optional<Professor> getProfessorOfClass(Long classId) {
        return classRepository.findById(classId).map(Class::getProfessor);
    }

    public Class addClassToDepartment(Long classId, Long departmentId) {
        Class classEntity = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new RuntimeException("Department not found"));

        classEntity.setDepartment((Department) department);
        return classRepository.save(classEntity);
    }

    public void enrollStudentInClass(Long classId, Long studentId) {
        Class classEntity = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

        student.getClasses().add(classEntity);
        studentRepository.save(student);
    }

    public void removeStudentFromClass(Long classId, Long studentId) {
        Class classEntity = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

        student.getClasses().remove(classEntity);
        studentRepository.save(student);
    }

    public Class assignProfessorToClass(Long classId, Long professorId) {
        Class classEntity = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new RuntimeException("Professor not found"));

        classEntity.setProfessor(professor);
        return classRepository.save(classEntity);
    }

}
