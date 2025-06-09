package org.mas.zoomanagementsystem.repository;

import org.mas.zoomanagementsystem.model.Animal;
import org.mas.zoomanagementsystem.model.MedicalTreatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for MedicalTreatment records, representing the history of treatments.
 */
@Repository
public interface MedicalTreatmentRepository extends JpaRepository<MedicalTreatment, Long> {

    /**
     * Finds all medical treatments for a specific animal, ordered by date descending.
     * @param animal The animal whose medical history is to be retrieved.
     * @return A list of medical treatments, with the most recent first.
     */
    List<MedicalTreatment> findByAnimalOrderByDateDesc(Animal animal);
}