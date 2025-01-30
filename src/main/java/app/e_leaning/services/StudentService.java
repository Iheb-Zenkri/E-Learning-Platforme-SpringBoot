package app.e_leaning.services;

import app.e_leaning.exceptions.ObjectNotFoundException;
import app.e_leaning.models.Classes;
import app.e_leaning.models.Student;
import app.e_leaning.models.User;
import app.e_leaning.repositories.StudentRepository;
import app.e_leaning.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository ;

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Student with id "+id+" not found"));
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id).map(student -> {
            User existingUser = student.getUser();

            if (existingUser != null && updatedStudent.getUser() != null) {
                existingUser.setFirstName(updatedStudent.getUser().getFirstName());
                existingUser.setLastName(updatedStudent.getUser().getLastName());
                existingUser.setEmail(updatedStudent.getUser().getEmail());
                existingUser.setLastLogin(updatedStudent.getUser().getLastLogin());

                userRepository.save(existingUser); // Save updated User
            }
            return studentRepository.save(student);
        }).orElseThrow(() -> new ObjectNotFoundException("Student with id "+id+" not found"));
    }

    public boolean deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Student with id "+id+" not found"));
        for(Classes cls : student.getClasses()){
            cls.getStudents().remove(student);
        }
        User user = student.getUser();

        studentRepository.delete(student);
        userRepository.delete(user);

        return !studentRepository.existsById(id);
    }

    public List<Classes> getStudentClasses(Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Student with id "+id+" not found"));
        return student.getClasses() ;
    }

}
