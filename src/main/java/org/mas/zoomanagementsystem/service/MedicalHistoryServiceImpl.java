package org.mas.zoomanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import org.mas.zoomanagementsystem.dto.AnimalListDto;
import org.mas.zoomanagementsystem.dto.MedicalTreatmentDetailDto;
import org.mas.zoomanagementsystem.dto.MedicalTreatmentSummaryDto;
import org.mas.zoomanagementsystem.model.Animal;
import org.mas.zoomanagementsystem.model.Employee;
import org.mas.zoomanagementsystem.model.MedicalTreatment;
import org.mas.zoomanagementsystem.repository.AnimalRepository;
import org.mas.zoomanagementsystem.repository.MedicalTreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final AnimalRepository animalRepository;
    private final MedicalTreatmentRepository medicalTreatmentRepository;

    @Autowired
    public MedicalHistoryServiceImpl(AnimalRepository animalRepository, MedicalTreatmentRepository medicalTreatmentRepository) {
        this.animalRepository = animalRepository;
        this.medicalTreatmentRepository = medicalTreatmentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnimalListDto> getAllAnimalsForListing() {
        return animalRepository.findAll().stream()
                .map(this::mapToAnimalListDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalTreatmentSummaryDto> getTreatmentsForAnimal(Long animalId) {
            Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new EntityNotFoundException("Animal not found with id: " + animalId));

        // This is the key part: navigating the object graph.
        // We get the treatments directly from the animal object.
        return animal.getMedicalTreatments().stream()
                .map(this::mapToMedicalTreatmentSummaryDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalTreatmentDetailDto getTreatmentDetails(Long treatmentId) {
        MedicalTreatment treatment = medicalTreatmentRepository.findById(treatmentId)
                .orElseThrow(() -> new EntityNotFoundException("Medical Treatment not found with id: " + treatmentId));

        return mapToMedicalTreatmentDetailDto(treatment);
    }

    // --- Private Helper Methods for Mapping ---

    private AnimalListDto mapToAnimalListDto(Animal animal) {
        return new AnimalListDto(
                animal.getId(),
                animal.getAnimalID(),
                animal.getGivenName(),
                animal.getSpecies() != null ? animal.getSpecies().getCommonName() : "N/A"
        );
    }

    private MedicalTreatmentSummaryDto mapToMedicalTreatmentSummaryDto(MedicalTreatment treatment) {
        return new MedicalTreatmentSummaryDto(
                treatment.getId(),
                treatment.getDate(),
                treatment.getType(),
                treatment.getDiagnosis()
        );
    }

    private MedicalTreatmentDetailDto mapToMedicalTreatmentDetailDto(MedicalTreatment treatment) {
        Employee vet = treatment.getPerformingEmployee();
        String vetFullName = "N/A";
        String vetSpecialization = "N/A";

        // This is another key part: navigating from Treatment to Employee.
        if (vet != null) {
            vetFullName = vet.getFirstName() + " " + vet.getLastName();
            vetSpecialization = vet.getSpecialization(); // Assuming specialisation is a field in Employee
        }

        return new MedicalTreatmentDetailDto(
                treatment.getId(),
                treatment.getDate(),
                treatment.getType(),
                treatment.getDiagnosis(),
                treatment.getProcedureDescription(),
                treatment.getMedicationAdministered(),
                treatment.getNotes(),
                vetFullName,
                vetSpecialization
        );
    }
}