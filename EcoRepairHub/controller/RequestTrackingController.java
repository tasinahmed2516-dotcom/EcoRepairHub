package com.ecorepairhub.controller;

import com.ecorepairhub.entity.RequestTracking;
import com.ecorepairhub.repository.RequestTrackingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/request-tracking")
@RequiredArgsConstructor
public class RequestTrackingController {

    private final RequestTrackingRepository requestTrackingRepository;

    @GetMapping("/request/{requestId}")
    public ResponseEntity<List<RequestTracking>> getHistoryForRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(requestTrackingRepository.findByRepairRequestIdOrderByUpdatedAtAsc(requestId));
    }
}
