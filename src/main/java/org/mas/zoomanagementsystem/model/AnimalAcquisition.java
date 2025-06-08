package org.mas.zoomanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class AnimalAcquisition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String acquisitionID;
    private LocalDate acquisitionDate;
    private String source;
    private String reason;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", unique = true)
    private Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zoo_id")
    private Zoo zoo;
}
