package org.mas.zoomanagementsystem.dto;

import lombok.*;
import org.mas.zoomanagementsystem.model.enums.MedicalTreatmentType;

import java.util.List;

/**
 * DTO for creating a new medical treatment record.
 * This object is typically populated from a form in the UI.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMedicalTreatmentDto {
    // IDs of the entities to be linked
    private Long animalId;
    private Long veterinarianId;

    // Data for the treatment itself
    private MedicalTreatmentType type;
    private String diagnosis;
    private String procedureDescription;
    private List<String> medicationAdministered;
    private String notes;
}