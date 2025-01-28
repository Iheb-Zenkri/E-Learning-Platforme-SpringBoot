package app.e_leaning.utils;

import app.e_leaning.dtos.ProfessorDTO;
import app.e_leaning.models.Professor;

public class ProfessorMapper {
    public static ProfessorDTO toProfessorDTO(Professor professor){
        ProfessorDTO dto = new ProfessorDTO();
        dto.setId(professor.getId());
        dto.setEmail(professor.getUser().getEmail());
        dto.setFirstName(professor.getUser().getFirstName());
        dto.setLastName(professor.getUser().getLastName());
        return dto ;
    }

    public static Professor toProfessorEntity(ProfessorDTO dto){
        Professor professor = new Professor();
        professor.setId(dto.getId());
        professor.getUser().setFirstName(dto.getFirstName());
        professor.getUser().setLastName(dto.getLastName());
        professor.getUser().setEmail(dto.getEmail());
        return professor ;
    }
}
