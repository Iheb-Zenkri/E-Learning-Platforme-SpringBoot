package app.e_leaning.dtos;

import app.e_leaning.models.Professor;
import app.e_leaning.models.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProfessorDTO {
    private Long id ;
    private String email;
    private String firstName;
    private String lastName;
    private Timestamp lastLogin;
    public static ProfessorDTO toProfessorDTO(Professor professor){
        ProfessorDTO dto = new ProfessorDTO();
        dto.setId(professor.getId());
        dto.setEmail(professor.getUser().getEmail());
        dto.setFirstName(professor.getUser().getFirstName());
        dto.setLastName(professor.getUser().getLastName());
        dto.setLastLogin(professor.getUser().getLastLogin());
        return dto ;
    }

    public static Professor toProfessorEntity(ProfessorDTO dto) {
        Professor professor = new Professor();
        professor.setId(dto.getId());

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        professor.setUser(user);
        return professor;
    }
}
