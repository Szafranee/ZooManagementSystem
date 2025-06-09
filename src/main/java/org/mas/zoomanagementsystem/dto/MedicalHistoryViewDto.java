package org.mas.zoomanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * A container DTO for the entire medical history view.
 * It holds the list of all animals, the list of treatments for the
 * selected animal, and the details of the selected treatment.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class MedicalHistoryViewDto {
    private List<AnimalListDto> animals;
    private Long selectedAnimalId;
    private List<MedicalTreatmentSummaryDto> treatments;
    private Long selectedTreatmentId;
    private MedicalTreatmentDetailDto treatmentDetail;
}
