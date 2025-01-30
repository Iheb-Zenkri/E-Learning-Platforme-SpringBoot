package app.e_leaning.utils;

import app.e_leaning.dtos.ProfessorDTO;
import app.e_leaning.models.Professor;
import app.e_leaning.models.User;

public class ProfessorMapper {
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
