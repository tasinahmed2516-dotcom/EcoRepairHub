package com.ecorepairhub.dto;

import com.ecorepairhub.enums.RequestStage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StageUpdateRequest {

    @NotNull
    private RequestStage stage;

    /** Free-text identifier of who made the update (e.g. "collector:12", "admin:3"). */
    @NotBlank
    private String handledBy;
}
