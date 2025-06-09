package org.mas.zoomanagementsystem.repository;

import org.mas.zoomanagementsystem.model.Zoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZooRepository extends JpaRepository<Zoo, Long> {
}
