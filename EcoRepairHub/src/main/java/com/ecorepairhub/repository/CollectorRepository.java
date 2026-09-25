package com.ecorepairhub.repository;

import com.ecorepairhub.entity.Collector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectorRepository extends JpaRepository<Collector, Long> {
    List<Collector> findByZone(String zone);
}
