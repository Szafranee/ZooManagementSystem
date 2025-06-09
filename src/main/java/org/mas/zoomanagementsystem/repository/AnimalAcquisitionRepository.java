package org.mas.zoomanagementsystem.repository;

import org.mas.zoomanagementsystem.model.AnimalAcquisition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalAcquisitionRepository extends JpaRepository<AnimalAcquisition, Long> {
}
