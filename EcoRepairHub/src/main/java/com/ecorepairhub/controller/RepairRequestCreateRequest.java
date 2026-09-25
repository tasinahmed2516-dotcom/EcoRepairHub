package com.ecorepairhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairRequestCreateRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String productType;
}
