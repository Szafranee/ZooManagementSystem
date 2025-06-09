package org.mas.zoomanagementsystem.repository;

import org.mas.zoomanagementsystem.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {
    Optional<Species> findByScientificNameIgnoreCase(String scientificName);
}
