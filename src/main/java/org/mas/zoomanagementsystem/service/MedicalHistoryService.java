package org.mas.zoomanagementsystem.service;

import org.mas.zoomanagementsystem.dto.*;

import java.util.List;

/**
 * Service interface for managing and retrieving animal medical history.
 * Defines the business logic operations related to the medical history use case.
 */
public interface MedicalHistoryService {

    /**
     * Retrieves a list of all animals, summarised for display.
     * @return A list of AnimalListDto objects.
     */
    List<AnimalListDto> getAllAnimalsForListing();

    /**
     * Retrieves a summary list of all medical treatments for a specific animal.
     * This method demonstrates navigating the object graph from Animal to MedicalTreatment.
     * @param animalId The ID of the animal.
     * @return A list of MedicalTreatmentSummaryDto objects.
     */
    List<MedicalTreatmentSummaryDto> getTreatmentsForAnimal(Long animalId);

    /**
     * Retrieves detailed information about a single medical treatment.
     * This method demonstrates navigating from MedicalTreatment to the associated Employee.
     * @param treatmentId The ID of the medical treatment.
     * @return A MedicalTreatmentDetailDto object.
     */
    MedicalTreatmentDetailDto getTreatmentDetails(Long treatmentId);

    /**
     * Creates and saves a new medical treatment record.
     * This method handles the business logic of creating a new treatment,
     * linking it to the correct animal and veterinarian, and persisting it.
     *
     * @param createDto DTO containing all necessary information for the new treatment.
     * @return The created MedicalTreatment entity, mapped to a detail DTO.
     */
    MedicalTreatmentDetailDto createNewTreatment(CreateMedicalTreatmentDto createDto);


    /**
     * Retrieves a list of all medical treatments performed by a specific veterinarian.
     * This method is used to view the treatments from the veterinarian's perspective.
     *
     * @param vetId The ID of the veterinarian.
     * @return A list of MedicalTreatmentSummaryDto objects for the specified veterinarian.
     */
    List<MedicalTreatmentSummaryDto> getTreatmentsByVeterinarian(Long vetId);

    /**
     * Retrieves detailed information about a specific animal.
     *
     * @param animalId The ID of the animal.
     * @return An AnimalDetailDto object containing the animal's details and associated veterinarian.
     */
    AnimalDetailDto getAnimalDetails(Long animalId);

    /**
     * Retrieves detailed information about a specific veterinarian.
     *
     * @param vetId The ID of the veterinarian.
     * @return A VeterinarianDetailDto object containing the veterinarian's details.
     */
    VeterinarianDetailDto getVeterinarianDetails(Long vetId);
}
