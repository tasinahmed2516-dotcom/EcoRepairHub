package com.ecorepairhub.repository;

import com.ecorepairhub.entity.RepairingCenter;
import com.ecorepairhub.enums.CenterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepairingCenterRepository extends JpaRepository<RepairingCenter, Long> {
    List<RepairingCenter> findByStatus(CenterStatus status);
}
