package com.ecorepairhub.entity;

import com.ecorepairhub.enums.RequestStage;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "request_tracking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // logs
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "request_id", nullable = false)
    private RepairRequest repairRequest;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStage stage;

    /** Free-text identifier of who made the update (e.g. "collector:12", "admin:3"). */
    @Column(nullable = false)
    private String handledBy;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        this.updatedAt = LocalDateTime.now();
    }
}
