package com.example.demo.api.repository;

import com.example.demo.api.domain.Drones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drones, Integer>, JpaSpecificationExecutor<Drones> {

}
