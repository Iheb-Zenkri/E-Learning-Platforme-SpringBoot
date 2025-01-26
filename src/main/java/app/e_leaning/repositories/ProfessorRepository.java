package app.e_leaning.repositories;

import app.e_leaning.models.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Page<Professor> findAllByPage(Pageable pageable);
    Page<Professor> findByDepartmentId(Long departmentId,Pageable pageable);
    Page<Professor> findBySchoolId(Long schoolId,Pageable pageable);
}
