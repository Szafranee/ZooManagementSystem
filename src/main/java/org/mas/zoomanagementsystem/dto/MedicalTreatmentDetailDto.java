package org.mas.zoomanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.mas.zoomanagementsystem.model.enums.MedicalTreatmentType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for displaying detailed information about a single medical treatment.
 * This includes details about the veterinarian who performed the treatment.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class MedicalTreatmentDetailDto {
    private Long id;
    private LocalDateTime date;
    private MedicalTreatmentType type;
    private String diagnosis;
    private String procedureDescription;
    private List<String> medicationAdministered;
    private String notes;
    private Long performingVetId;
    private String performingVetFullName; // Aggregated data
    private String performingVetSpecialization; // Aggregated data
    private Long patientId;
    private String patientIdString;
    private String patientName;
}