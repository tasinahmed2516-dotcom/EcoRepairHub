package com.ecorepairhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "collectors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    private String phone;

    @Column(nullable = false)
    private String zone;

    @Builder.Default
    @OneToMany(mappedBy = "collector", cascade = CascadeType.ALL)
    private List<RepairRequest> repairRequests = new ArrayList<>();
}
