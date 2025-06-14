package org.mas.zoomanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import org.mas.zoomanagementsystem.dto.*;
import org.mas.zoomanagementsystem.model.Animal;
import org.mas.zoomanagementsystem.model.Employee;
import org.mas.zoomanagementsystem.model.MedicalTreatment;
import org.mas.zoomanagementsystem.model.enums.EmployeeRole;
import org.mas.zoomanagementsystem.repository.AnimalRepository;
import org.mas.zoomanagementsystem.repository.EmployeeRepository;
import org.mas.zoomanagementsystem.repository.MedicalTreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final AnimalRepository animalRepository;
    private final MedicalTreatmentRepository medicalTreatmentRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public MedicalHistoryServiceImpl(AnimalRepository animalRepository, MedicalTreatmentRepository medicalTreatmentRepository, EmployeeRepository employeeRepository) {
        this.animalRepository = animalRepository;
        this.medicalTreatmentRepository = medicalTreatmentRepository;
        this.employeeRepository = employeeRepository;
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

    @Override
    @Transactional
    public MedicalTreatmentDetailDto createNewTreatment(CreateMedicalTreatmentDto createDto) {
        Animal animal = animalRepository.findById(createDto.getAnimalId())
                .orElseThrow(() -> new EntityNotFoundException("Animal not found with id: " + createDto.getAnimalId()));

        Employee veterinarian = employeeRepository.findById(createDto.getVeterinarianId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + createDto.getVeterinarianId()));

        if (!veterinarian.getRoles().contains(EmployeeRole.VETERINARIAN)) {
            // TODO: Custom exception
            throw new IllegalArgumentException("The assigned employee is not a veterinarian.");
        }

        MedicalTreatment newTreatment = new MedicalTreatment();
        newTreatment.setDate(LocalDateTime.now());
        newTreatment.setType(createDto.getType());
        newTreatment.setDiagnosis(createDto.getDiagnosis());
        newTreatment.setProcedureDescription(createDto.getProcedureDescription());
        newTreatment.setMedicationAdministered(createDto.getMedicationAdministered());
        newTreatment.setNotes(createDto.getNotes());

        newTreatment.setAnimal(animal);
        newTreatment.setPerformingEmployee(veterinarian);

        MedicalTreatment savedTreatment = medicalTreatmentRepository.save(newTreatment);

        return mapToMedicalTreatmentDetailDto(savedTreatment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalTreatmentSummaryDto> getTreatmentsByVeterinarian(Long vetId) {
        Employee veterinarian = employeeRepository.findById(vetId)
                .orElseThrow(() -> new EntityNotFoundException("Veterinarian not found with id: " + vetId));

        if (!veterinarian.getRoles().contains(EmployeeRole.VETERINARIAN)) {
            // TODO: Custom exception
            throw new IllegalArgumentException("The assigned employee is not a veterinarian.");
        }

        return medicalTreatmentRepository.findByPerformingEmployee(veterinarian).stream()
                .map(this::mapToMedicalTreatmentSummaryDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AnimalDetailDto getAnimalDetails(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new EntityNotFoundException("Animal not found with id: " + animalId));
        return mapToAnimalDetailDto(animal);
    }

    @Override
    @Transactional(readOnly = true)
    public VeterinarianDetailDto getVeterinarianDetails(Long vetId) {
        Employee vet = employeeRepository.findById(vetId)
                .orElseThrow(() -> new EntityNotFoundException("Veterinarian not found with id: " + vetId));
        // Pobieramy zabiegi, aby policzyć ich liczbę
        List<MedicalTreatment> treatments = medicalTreatmentRepository.findByPerformingEmployeeOrderByDateDesc(vet);
        return mapToVeterinarianDetailDto(vet, treatments.size());
    }


    // --- Private Helper Methods for Mapping ---

    private AnimalListDto mapToAnimalListDto(Animal animal) {
        // Using the builder for cleaner object creation
        return AnimalListDto.builder()
                .id(animal.getId())
                .animalID(animal.getAnimalID())
                .givenName(animal.getGivenName())
                .speciesCommonName(animal.getSpecies() != null ? animal.getSpecies().getCommonName() : "N/A")
                .build();
    }

    private MedicalTreatmentSummaryDto mapToMedicalTreatmentSummaryDto(MedicalTreatment treatment) {
        // Using the builder for cleaner object creation
        return MedicalTreatmentSummaryDto.builder()
                .id(treatment.getId())
                .date(treatment.getDate())
                .type(treatment.getType())
                .diagnosis(treatment.getDiagnosis())
                .build();
    }

    private MedicalTreatmentDetailDto mapToMedicalTreatmentDetailDto(MedicalTreatment treatment) {
        Employee vet = treatment.getPerformingEmployee();
        Animal patient = treatment.getAnimal();

        return MedicalTreatmentDetailDto.builder()
                .id(treatment.getId())
                .date(treatment.getDate())
                .type(treatment.getType())
                .diagnosis(treatment.getDiagnosis())
                .procedureDescription(treatment.getProcedureDescription())
                .medicationAdministered(treatment.getMedicationAdministered())
                .notes(treatment.getNotes())
                .performingVetId(vet != null ? vet.getId() : null)
                .performingVetFullName(vet != null ? vet.getFirstName() + " " + vet.getLastName() : "N/A")
                .performingVetSpecialization(vet != null ? vet.getSpecialization() : "N/A")
                .patientId(patient != null ? patient.getId() : null)
                .patientName(patient != null ? patient.getGivenName() : "N/A")
                .patientIdString(patient != null ? patient.getAnimalID() : "N/A")
                .build();
    }

    private AnimalDetailDto mapToAnimalDetailDto(Animal animal) {
        return AnimalDetailDto.builder()
                .givenName(animal.getGivenName())
                .animalID(animal.getAnimalID())
                .speciesCommonName(animal.getSpecies().getCommonName())
                .age(animal.getAge())
                .gender(animal.getGender())
                .dateOfBirth(animal.getDateOfBirth())
                .origin(animal.getOrigin())
                .healthStatus(animal.getHealthStatus())
                .enclosureName(animal.getEnclosure() != null ? animal.getEnclosure().getName() : "Unassigned")
                .build();
    }

    private VeterinarianDetailDto mapToVeterinarianDetailDto(Employee vet, int treatmentCount) {
        return VeterinarianDetailDto.builder()
                .fullName(vet.getFirstName() + " " + vet.getLastName())
                .email(vet.getEmail())
                .specialization(vet.getSpecialization())
                .licenseNumber(vet.getLicenseNumber())
                .numberOfTreatmentsPerformed(treatmentCount)
                .build();
    }
}