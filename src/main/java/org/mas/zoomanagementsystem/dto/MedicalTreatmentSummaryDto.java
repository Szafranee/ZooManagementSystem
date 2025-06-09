package org.mas.zoomanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.mas.zoomanagementsystem.model.enums.MedicalTreatmentType;

import java.time.LocalDateTime;

/**
 * DTO for displaying a summary of a medical treatment in a list.
 * Represents one entry in an animal's medical history.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class MedicalTreatmentSummaryDto {
    private Long id;
    private LocalDateTime date;
    private MedicalTreatmentType type;
    private String diagnosis;
}