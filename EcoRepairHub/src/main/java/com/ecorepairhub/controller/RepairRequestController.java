package com.ecorepairhub.controller;

import com.ecorepairhub.dto.RepairRequestCreateRequest;
import com.ecorepairhub.dto.StageUpdateRequest;
import com.ecorepairhub.entity.*;
import com.ecorepairhub.enums.RequestStage;
import com.ecorepairhub.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repair-requests")
@RequiredArgsConstructor
public class RepairRequestController {

    private final RepairRequestRepository repairRequestRepository;
    private final RequestTrackingRepository requestTrackingRepository;
    private final UserRepository userRepository;
    private final CollectorRepository collectorRepository;
    private final AdminRepository adminRepository;
    private final RepairingCenterRepository repairingCenterRepository;

    @PostMapping
    public ResponseEntity<RepairRequest> createRequest(@Valid @RequestBody RepairRequestCreateRequest body) {
        User user = userRepository.findById(body.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + body.getUserId()));

        RepairRequest request = RepairRequest.builder()
                .user(user)
                .productType(body.getProductType())
                .currentStage(RequestStage.SUBMITTED)
                .build();
        RepairRequest saved = repairRequestRepository.save(request);

        logStage(saved, RequestStage.SUBMITTED, "user:" + user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<RepairRequest>> getAllRequests() {
        return ResponseEntity.ok(repairRequestRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairRequest> getRequestById(@PathVariable Long id) {
        return repairRequestRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RepairRequest>> getRequestsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(repairRequestRepository.findByUserId(userId));
    }

    @GetMapping("/collector/{collectorId}")
    public ResponseEntity<List<RepairRequest>> getRequestsByCollector(@PathVariable Long collectorId) {
        return ResponseEntity.ok(repairRequestRepository.findByCollectorId(collectorId));
    }

    @GetMapping("/center/{centerId}")
    public ResponseEntity<List<RepairRequest>> getRequestsByCenter(@PathVariable Long centerId) {
        return ResponseEntity.ok(repairRequestRepository.findByCenterId(centerId));
    }

    @GetMapping("/stage/{stage}")
    public ResponseEntity<List<RepairRequest>> getRequestsByStage(@PathVariable RequestStage stage) {
        return ResponseEntity.ok(repairRequestRepository.findByCurrentStage(stage));
    }

    @PatchMapping("/{id}/assign-collector/{collectorId}")
    public ResponseEntity<RepairRequest> assignCollector(@PathVariable Long id, @PathVariable Long collectorId) {
        RepairRequest request = repairRequestRepository.findById(id).orElse(null);
        if (request == null) {
            return ResponseEntity.notFound().build();
        }
        Collector collector = collectorRepository.findById(collectorId)
                .orElseThrow(() -> new IllegalArgumentException("Collector not found: " + collectorId));
        request.setCollector(collector);
        return ResponseEntity.ok(repairRequestRepository.save(request));
    }

    @PatchMapping("/{id}/assign-center/{centerId}")
    public ResponseEntity<RepairRequest> assignCenter(@PathVariable Long id, @PathVariable Long centerId) {
        RepairRequest request = repairRequestRepository.findById(id).orElse(null);
        if (request == null) {
            return ResponseEntity.notFound().build();
        }
        RepairingCenter center = repairingCenterRepository.findById(centerId)
                .orElseThrow(() -> new IllegalArgumentException("Repairing center not found: " + centerId));
        request.setCenter(center);
        return ResponseEntity.ok(repairRequestRepository.save(request));
    }

    @PatchMapping("/{id}/assign-admin/{adminId}")
    public ResponseEntity<RepairRequest> assignAdmin(@PathVariable Long id, @PathVariable Long adminId) {
        RepairRequest request = repairRequestRepository.findById(id).orElse(null);
        if (request == null) {
            return ResponseEntity.notFound().build();
        }
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found: " + adminId));
        request.setAdmin(admin);
        return ResponseEntity.ok(repairRequestRepository.save(request));
    }

    @PatchMapping("/{id}/stage")
    public ResponseEntity<RepairRequest> updateStage(@PathVariable Long id, @Valid @RequestBody StageUpdateRequest body) {
        RepairRequest request = repairRequestRepository.findById(id).orElse(null);
        if (request == null) {
            return ResponseEntity.notFound().build();
        }
        request.setCurrentStage(body.getStage());
        RepairRequest saved = repairRequestRepository.save(request);
        logStage(saved, body.getStage(), body.getHandledBy());
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        if (!repairRequestRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repairRequestRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void logStage(RepairRequest request, RequestStage stage, String handledBy) {
        RequestTracking log = RequestTracking.builder()
                .repairRequest(request)
                .stage(stage)
                .handledBy(handledBy)
                .build();
        requestTrackingRepository.save(log);
    }
}
