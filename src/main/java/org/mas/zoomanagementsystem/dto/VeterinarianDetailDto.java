package org.mas.zoomanagementsystem.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VeterinarianDetailDto {
    private String fullName;
    private String email;
    private String specialization;
    private String licenseNumber;
    private int numberOfTreatmentsPerformed;
}