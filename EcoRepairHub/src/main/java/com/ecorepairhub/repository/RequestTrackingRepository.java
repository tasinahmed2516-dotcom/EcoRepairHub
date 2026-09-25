package com.ecorepairhub.repository;

import com.ecorepairhub.entity.RequestTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestTrackingRepository extends JpaRepository<RequestTracking, Long> {
    List<RequestTracking> findByRepairRequestIdOrderByUpdatedAtAsc(Long requestId);
}
