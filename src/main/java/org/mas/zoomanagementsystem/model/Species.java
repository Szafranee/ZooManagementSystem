package org.mas.zoomanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mas.zoomanagementsystem.model.enums.ConservationStatus;

import java.util.List;

@Entity
@Getter
@Setter
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String scientificName;
    private String commonName;
    @Enumerated(EnumType.STRING)
    private ConservationStatus conservationStatus;
    private String naturalHabitatDescription;
    private int averageLifespan;
    @ElementCollection
    private List<String> typicalDiet;
}
