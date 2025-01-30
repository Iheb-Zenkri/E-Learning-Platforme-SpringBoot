package app.e_leaning.services;

import app.e_leaning.exceptions.ObjectNotFoundException;
import app.e_leaning.models.Classes;
import app.e_leaning.models.Department;
import app.e_leaning.models.Professor;
import app.e_leaning.models.Student;
import app.e_leaning.repositories.ClassesRepository;
import app.e_leaning.repositories.DepartmentRepository;
import app.e_leaning.repositories.ProfessorRepository;
import app.e_leaning.repositories.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClassesService {

    private final ClassesRepository classesRepository;

    private final StudentRepository studentRepository;

    private final DepartmentRepository departmentRepository ;

    private final ProfessorRepository professorRepository ;

    public ClassesService(ClassesRepository classesRepository, StudentRepository studentRepository, DepartmentRepository departmentRepository, ProfessorRepository professorRepository) {
        this.classesRepository = classesRepository;
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.professorRepository = professorRepository;
    }

    public Classes createClass(Classes newClasses) {
        return classesRepository.save(newClasses);
    }

    public Classes getClassById(Long id) {
        return classesRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Class with id "+id+" not found"));
    }

    public Classes updateClass(Long id, Classes updatedClasses) {
        return classesRepository.findById(id).map(existingClass -> {
            existingClass.setTitle(updatedClasses.getTitle());
            existingClass.setSubject(updatedClasses.getSubject());
            existingClass.setSemester(updatedClasses.getSemester());
            return classesRepository.save(existingClass);
        }).orElseThrow(() -> new ObjectNotFoundException("Class with id "+id+" not found"));
    }

    public Boolean deleteClass(Long id) {
        classesRepository.deleteById(id);
        return !classesRepository.existsById(id);
    }


    public Page<Classes> getAllClasses(Pageable pageable) {
        return classesRepository.findAll(pageable);
    }

    public Page<Student> getStudentsInClass(Long classId, Pageable pageable) {
        return studentRepository.findByClassesId(classId,pageable);
    }

    public Professor getProfessorOfClass(Long classId) {
        return classesRepository.findById(classId).map(Classes::getProfessor)
                .orElseThrow(() -> new ObjectNotFoundException("Class with id "+classId+" do not have a professor"));
    }

    public Department getDepartmentOfClass(Long id){
        return classesRepository.findById(id).map(Classes::getDepartment)
                .orElseThrow(() -> new ObjectNotFoundException("Class with id "+id+" do not have a department"));
    }

    public Classes addClassToDepartment(Long classId, Long departmentId) {
        Classes classesEntity = classesRepository.findById(classId).orElseThrow(() -> new ObjectNotFoundException("Class with id "+classId+" not found"));
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new ObjectNotFoundException("Department with id "+departmentId+" not found"));
        classesEntity.setDepartment(department);
        return classesRepository.save(classesEntity);
    }

    public boolean enrollStudentInClass(Long classId, Long studentId) {
        Classes classesEntity = classesRepository.findById(classId).orElseThrow(() -> new ObjectNotFoundException("Class with id "+classId+" not found"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ObjectNotFoundException("Student with id "+studentId+" not found"));
        student.getClasses().add(classesEntity);
        studentRepository.save(student);

        Student updatedStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found after update"));

        return updatedStudent.getClasses().contains(classesEntity);
    }

    public boolean removeStudentFromClass(Long classId, Long studentId) {
        Classes classesEntity = classesRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

        if (!student.getClasses().contains(classesEntity)) {
            throw new ObjectNotFoundException("Student with id "+student+"not found in Class with id "+classId);
        }
        student.getClasses().remove(classesEntity);
        studentRepository.save(student);

        Student updatedStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found after update"));

        return !updatedStudent.getClasses().contains(classesEntity);
    }

    public Classes assignProfessorToClass(Long classId, Long professorId) {
        Classes classesEntity = classesRepository.findById(classId).orElseThrow(() -> new ObjectNotFoundException("Class with id "+classId+" not found"));
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new ObjectNotFoundException("Professor with id "+professorId+" not found"));
        classesEntity.setProfessor(professor);
        return classesRepository.save(classesEntity);
    }

}
