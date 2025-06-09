package org.mas.zoomanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for displaying a summary of an animal in a list.
 * Contains minimal data needed for identification and selection in the UI.
 */
@Getter
@Setter
@AllArgsConstructor
public class AnimalListDto {
    private Long id;
    private String animalID;
    private String givenName;
    private String speciesCommonName;
}
