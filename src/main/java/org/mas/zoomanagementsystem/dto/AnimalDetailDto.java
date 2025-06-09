package org.mas.zoomanagementsystem.dto;

import lombok.Builder;
import lombok.Getter;
import org.mas.zoomanagementsystem.model.enums.Gender;
import org.mas.zoomanagementsystem.model.enums.HealthStatus;

import java.time.LocalDate;

@Getter
@Builder
public class AnimalDetailDto {
    private String givenName;
    private String animalID;
    private String speciesCommonName;
    private int age;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String origin;
    private HealthStatus healthStatus;
    private String enclosureName;
}
