package com.ecorepairhub.entity;

import com.ecorepairhub.enums.CenterStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "repairing_centers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairingCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CenterStatus status;

    @Builder.Default
    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    private List<RepairRequest> repairRequests = new ArrayList<>();
}
