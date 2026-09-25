package com.ecorepairhub.controller;

import com.ecorepairhub.entity.Collector;
import com.ecorepairhub.repository.CollectorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collectors")
@RequiredArgsConstructor
public class CollectorController {

    private final CollectorRepository collectorRepository;

    @PostMapping
    public ResponseEntity<Collector> createCollector(@Valid @RequestBody Collector collector) {
        collector.setId(null);
        Collector saved = collectorRepository.save(collector);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Collector>> getAllCollectors() {
        return ResponseEntity.ok(collectorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collector> getCollectorById(@PathVariable Long id) {
        return collectorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<Collector>> getCollectorsByZone(@PathVariable String zone) {
        return ResponseEntity.ok(collectorRepository.findByZone(zone));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Collector> updateCollector(@PathVariable Long id, @Valid @RequestBody Collector updated) {
        return collectorRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setPhone(updated.getPhone());
                    existing.setZone(updated.getZone());
                    return ResponseEntity.ok(collectorRepository.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollector(@PathVariable Long id) {
        if (!collectorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        collectorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
