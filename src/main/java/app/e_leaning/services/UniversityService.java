package app.e_leaning.services;

import app.e_leaning.models.University;
import app.e_leaning.models.School;
import app.e_leaning.repositories.UniversityRepository;
import app.e_leaning.repositories.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UniversityService {

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    public University createUniversity(University university) {
        return universityRepository.save(university);
    }

    public Optional<University> getUniversityById(Long id) {
        return universityRepository.findById(id);
    }

    public University updateUniversity(Long id, University updatedUniversity) {
        return universityRepository.findById(id).map(university -> {
            university.setName(updatedUniversity.getName());
            university.setAddress(updatedUniversity.getAddress());
            return universityRepository.save(university);
        }).orElseThrow(() -> new RuntimeException("University not found"));
    }

    public void deleteUniversity(Long id) {
        universityRepository.deleteById(id);
    }

    public Page<University> getAllUniversities(Pageable pageable) {
        return universityRepository.findAll(pageable);
    }

    public Page<School> getSchoolsByUniversity(Long universityId,Pageable pageable) {
        return schoolRepository.findByUniversityId(universityId,pageable);
    }
}
