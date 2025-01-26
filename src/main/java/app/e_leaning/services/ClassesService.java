package app.e_leaning.services;

import app.e_leaning.models.Classes;
import app.e_leaning.models.Department;
import app.e_leaning.models.Professor;
import app.e_leaning.models.Student;
import app.e_leaning.repositories.ClassesRepository;
import app.e_leaning.repositories.DepartmentRepository;
import app.e_leaning.repositories.ProfessorRepository;
import app.e_leaning.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository ;

    @Autowired
    private ProfessorRepository professorRepository ;

    public Classes createClass(Classes newClasses) {
        return classesRepository.save(newClasses);
    }

    public Optional<Classes> getClassById(Long id) {
        return classesRepository.findById(id);
    }

    public Classes updateClass(Long id, Classes updatedClasses) {
        return classesRepository.findById(id).map(existingClass -> {
            existingClass.setTitle(updatedClasses.getTitle());
            existingClass.setDescription(updatedClasses.getDescription());
            existingClass.setProfessor(updatedClasses.getProfessor());
            existingClass.setSemester(updatedClasses.getSemester());
            existingClass.setDepartment(updatedClasses.getDepartment());
            return classesRepository.save(existingClass);
        }).orElseThrow(() -> new RuntimeException("Class not found"));
    }

    public void deleteClass(Long id) {
        classesRepository.deleteById(id);
    }


    public Page<Classes> getAllClasses(Pageable pageable) {
        return classesRepository.findAll(pageable);
    }

    public Page<Student> getStudentsInClass(Long classId, Pageable pageable) {
        return studentRepository.findByClassesId(classId,pageable);
    }

    public Optional<Professor> getProfessorOfClass(Long classId) {
        return classesRepository.findById(classId).map(Classes::getProfessor);
    }

    public Classes addClassToDepartment(Long classId, Long departmentId) {
        Classes classesEntity = classesRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new RuntimeException("Department not found"));

        classesEntity.setDepartment((Department) department);
        return classesRepository.save(classesEntity);
    }

    public void enrollStudentInClass(Long classId, Long studentId) {
        Classes classesEntity = classesRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

        student.getClasses().add(classesEntity);
        studentRepository.save(student);
    }

    public void removeStudentFromClass(Long classId, Long studentId) {
        Classes classesEntity = classesRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

        student.getClasses().remove(classesEntity);
        studentRepository.save(student);
    }

    public Classes assignProfessorToClass(Long classId, Long professorId) {
        Classes classesEntity = classesRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new RuntimeException("Professor not found"));

        classesEntity.setProfessor(professor);
        return classesRepository.save(classesEntity);
    }

}
