package app.e_leaning.repositories;

import app.e_leaning.models.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    @Query("SELECT DISTINCT p FROM Professor p JOIN p.classes c JOIN c.department d WHERE d.university.id = :universityId")
    Page<Professor> findProfessorsByUniversityId(@Param("universityId") Long universityId, Pageable pageable);

}
