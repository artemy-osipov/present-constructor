package io.github.artemy.osipov.shop.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class DPresent {
    private UUID id;
    private String name;
    private BigDecimal price;
    private LocalDateTime date;
    private List<DPresentItem> items = new ArrayList<>();
}
