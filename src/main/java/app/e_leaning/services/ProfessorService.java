package app.e_leaning.services;

import app.e_leaning.exceptions.ObjectNotFoundException;
import app.e_leaning.models.Classes;
import app.e_leaning.models.Professor;
import app.e_leaning.models.User;
import app.e_leaning.repositories.ProfessorRepository;
import app.e_leaning.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private UserRepository userRepository ;

    public Professor getProfessorById(Long id) {
        return professorRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Professor with id "+id+" not found"));
    }

    public Professor updateProfessor(Long id, Professor updatedProfessor) {
        return professorRepository.findById(id).map(professor -> {
            User existingUser = professor.getUser(); // Get the existing User

            if (existingUser != null && updatedProfessor.getUser() != null) {
                // Update only the fields that changed
                existingUser.setFirstName(updatedProfessor.getUser().getFirstName());
                existingUser.setLastName(updatedProfessor.getUser().getLastName());
                existingUser.setEmail(updatedProfessor.getUser().getEmail());
                existingUser.setLastLogin(updatedProfessor.getUser().getLastLogin());

                userRepository.save(existingUser); // Save updated User
            }

            return professorRepository.save(professor); // Save updated Professor
        }).orElseThrow(() -> new ObjectNotFoundException("Professor with id " + id + " not found"));
    }
    public boolean deleteProfessor(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Professor with id " + id + " not found"));

        for (Classes cls : professor.getClasses()) {
            cls.setProfessor(null);
        }
        User user = professor.getUser();

        professorRepository.delete(professor);
        userRepository.delete(user);

        return !professorRepository.existsById(id);
    }

    public List<Classes> getClassesTaughtByProfessor(Long professorId) {
        Optional<Professor> professor = professorRepository.findById(professorId);
        if (professor.isPresent()) {
            return professor.get().getClasses();
        } else {
            throw new ObjectNotFoundException("Professor with id "+professorId+" not found");
        }
    }
}
