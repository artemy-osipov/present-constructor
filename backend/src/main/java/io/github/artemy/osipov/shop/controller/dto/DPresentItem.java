package io.github.artemy.osipov.shop.controller.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class DPresentItem {

    @NotNull
    @Valid
    private UUID candyId;

    @NotNull
    @Min(1)
    private Integer count;
}
