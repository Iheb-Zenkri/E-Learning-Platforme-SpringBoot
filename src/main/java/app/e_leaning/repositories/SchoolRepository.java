package app.e_leaning.repositories;

import app.e_leaning.models.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    Page<School> findAllByPage(Pageable pageable);
    Page<School> findByUniversityId(Long universityId,Pageable pageable);
}
