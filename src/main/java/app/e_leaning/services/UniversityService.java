package app.e_leaning.services;

import app.e_leaning.dtos.DepartmentDTO;
import app.e_leaning.dtos.UniversityDTO;
import app.e_leaning.exceptions.ObjectNotFoundException;
import app.e_leaning.models.Department;
import app.e_leaning.models.University;
import app.e_leaning.repositories.DepartmentRepository;
import app.e_leaning.repositories.UniversityRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static app.e_leaning.dtos.UniversityDTO.toUniversityDTO;

@Service
@Slf4j
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final DepartmentRepository departmentRepository;

    public UniversityService(UniversityRepository universityRepository, DepartmentRepository departmentRepository) {
        this.universityRepository = universityRepository;
        this.departmentRepository = departmentRepository;
    }

    public University createUniversity(University university) {
        return universityRepository.save(university);
    }

    public UniversityDTO getUniversityById(Long id) {
        return universityRepository.findById(id).map(UniversityDTO::toUniversityDTO
        ).orElseThrow(()-> new ObjectNotFoundException("University not found with id: "+id));
    }

    public UniversityDTO updateUniversity(Long id, UniversityDTO updatedDTO) {
        return universityRepository.findById(id).map(university -> {
            university.setName(updatedDTO.getName());
            university.setAddress(updatedDTO.getAddress());
            University updatedUniversity = universityRepository.save(university);
            return toUniversityDTO(updatedUniversity);
        }).orElseThrow(() -> new ObjectNotFoundException("University not found with id: "+id));
    }

    @Transactional
    public boolean deleteUniversity(Long id) {
        if (!universityRepository.existsById(id)) {
            throw new ObjectNotFoundException("University not found with ID: " + id);
        }
        try {
            universityRepository.deleteById(id);
            return !universityRepository.existsById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting the university", e);
        }
    }

    public Page<UniversityDTO> getAllUniversities(Pageable pageable) {
        return universityRepository.findAll(pageable).map(UniversityDTO::toUniversityDTO);
    }

    public Page<DepartmentDTO> getDepartmentByUniversity(Long universityId, Pageable pageable) {
        return departmentRepository.findByUniversityId(universityId,pageable).map(DepartmentDTO::toDepartmentDTO);
    }

    @Transactional
    public University addDepartementToUniversity(Long universityId, Long departmentId) {
        return universityRepository.findById(universityId).map(university -> {
            Department newDepartment = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new ObjectNotFoundException("Department with id "+departmentId+" not found."));
            newDepartment.setUniversity(university);
            university.getDepartments().add(newDepartment);
            return universityRepository.save(university);
        }).orElseThrow(() -> new ObjectNotFoundException("University not found with ID: " + universityId));
    }

    @Transactional
    public University removeDepartementFromUniversity(Long universityId, Long departmentId) {
        return universityRepository.findById(universityId).map(university -> {
            Department departmentToRemove = university.getDepartments().stream()
                    .filter(department -> department.getId().equals(departmentId))
                    .findFirst()
                    .orElseThrow(() -> new ObjectNotFoundException("Department not found with ID: " + departmentId));
            departmentToRemove.setUniversity(null);
            university.getDepartments().remove(departmentToRemove);
            return universityRepository.save(university);
        }).orElseThrow(() -> new ObjectNotFoundException("University not found with ID: " + universityId));
    }

}
