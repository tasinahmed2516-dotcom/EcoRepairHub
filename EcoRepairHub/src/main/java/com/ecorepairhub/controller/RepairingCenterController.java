package com.ecorepairhub.controller;

import com.ecorepairhub.entity.RepairingCenter;
import com.ecorepairhub.enums.CenterStatus;
import com.ecorepairhub.repository.RepairingCenterRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repairing-centers")
@RequiredArgsConstructor
public class RepairingCenterController {

    private final RepairingCenterRepository repairingCenterRepository;

    @PostMapping
    public ResponseEntity<RepairingCenter> createCenter(@Valid @RequestBody RepairingCenter center) {
        center.setId(null);
        RepairingCenter saved = repairingCenterRepository.save(center);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<RepairingCenter>> getAllCenters() {
        return ResponseEntity.ok(repairingCenterRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairingCenter> getCenterById(@PathVariable Long id) {
        return repairingCenterRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RepairingCenter>> getCentersByStatus(@PathVariable CenterStatus status) {
        return ResponseEntity.ok(repairingCenterRepository.findByStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepairingCenter> updateCenter(@PathVariable Long id, @Valid @RequestBody RepairingCenter updated) {
        return repairingCenterRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setAddress(updated.getAddress());
                    existing.setStatus(updated.getStatus());
                    return ResponseEntity.ok(repairingCenterRepository.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCenter(@PathVariable Long id) {
        if (!repairingCenterRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repairingCenterRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
