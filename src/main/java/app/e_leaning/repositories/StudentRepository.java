package app.e_leaning.repositories;

import app.e_leaning.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findAllByPage(Pageable pageable);
    Page<Student> findByDepartmentId(Long departmentId,Pageable pageable);
    Page<Student> findBySchoolId(Long schoolId,Pageable pageable);
    Page<Student> findByClassId(Long classId,Pageable pageable);
}
