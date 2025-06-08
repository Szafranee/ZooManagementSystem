package org.mas.zoomanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mas.zoomanagementsystem.model.enums.Gender;
import org.mas.zoomanagementsystem.model.enums.HealthStatus;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String animalID;
    private String givenName;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String origin;
    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id", nullable = false)
    private Species species;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enclosure_id")
    private Enclosure enclosure;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalTreatment> medicalTreatments = new ArrayList<>();

    /**
     * Calculates the age of the animal based on the date of birth.
     * Returns 0 if the date of birth is not set.
     *
     * @return the age in years
     */
    @Transient
    public int getAge() {
        if (this.dateOfBirth == null) {
            return 0;
        }
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }
}
