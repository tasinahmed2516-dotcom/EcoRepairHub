package com.ecorepairhub.repository;

import com.ecorepairhub.entity.RepairRequest;
import com.ecorepairhub.enums.RequestStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepairRequestRepository extends JpaRepository<RepairRequest, Long> {
    List<RepairRequest> findByUserId(Long userId);
    List<RepairRequest> findByCollectorId(Long collectorId);
    List<RepairRequest> findByCenterId(Long centerId);
    List<RepairRequest> findByCurrentStage(RequestStage stage);
}
