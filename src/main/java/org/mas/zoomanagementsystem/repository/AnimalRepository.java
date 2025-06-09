package org.mas.zoomanagementsystem.repository;

import org.mas.zoomanagementsystem.model.Animal;
import org.mas.zoomanagementsystem.model.Enclosure;
import org.mas.zoomanagementsystem.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

     /**
     * Finds an animal by its unique business identifier.
     * @param animalID The unique ID string of the animal.
     * @return An Optional containing the found animal or empty if not found.
     */
    Optional<Animal> findByAnimalID(String animalID);

    /**
     * Finds all animals belonging to a specific species.
     * @param species The Species entity to search by.
     * @return A list of animals of the given species.
     */
    List<Animal> findBySpecies(Species species);

    /**
     * Finds all animals currently housed in a specific enclosure.
     * @param enclosure The Enclosure entity to search by.
     * @return A list of animals in the given enclosure.
     */
    List<Animal> findByEnclosure(Enclosure enclosure);
}