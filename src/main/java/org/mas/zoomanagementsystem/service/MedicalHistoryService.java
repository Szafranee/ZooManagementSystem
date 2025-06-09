package org.mas.zoomanagementsystem.service;

import org.mas.zoomanagementsystem.dto.AnimalListDto;
import org.mas.zoomanagementsystem.dto.MedicalTreatmentDetailDto;
import org.mas.zoomanagementsystem.dto.MedicalTreatmentSummaryDto;

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
}
