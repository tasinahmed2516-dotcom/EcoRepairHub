package com.ecorepairhub.entity;

import com.ecorepairhub.enums.RequestStage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "repair_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // submits
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // collects
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collector_id")
    private Collector collector;

    // approves
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    // repairs
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private RepairingCenter center;

    @NotBlank
    @Column(nullable = false)
    private String productType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStage currentStage;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // logs
    @Builder.Default
    @OneToMany(mappedBy = "repairRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestTracking> trackingLogs = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.currentStage == null) {
            this.currentStage = RequestStage.SUBMITTED;
        }
    }
}
