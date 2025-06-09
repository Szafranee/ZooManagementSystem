package org.mas.zoomanagementsystem.repository;

import org.mas.zoomanagementsystem.model.Enclosure;
import org.mas.zoomanagementsystem.model.Zoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnclosureRepository extends JpaRepository<Enclosure, Long> {

    /**
     * Finds an enclosure by its name within a specific zoo.
     * This implements the logic of the qualified association from the UML diagram.
     * @param name The name of the enclosure.
     * @param zoo The zoo the enclosure belongs to.
     * @return An Optional containing the found enclosure or empty if not found.
     */
    Optional<Enclosure> findByNameAndZoo(String name, Zoo zoo);
}
