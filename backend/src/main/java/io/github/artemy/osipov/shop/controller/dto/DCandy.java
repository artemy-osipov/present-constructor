package io.github.artemy.osipov.shop.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class DCandy {
    private UUID id;
    private String name;
    private String firm;
    private BigDecimal price;
    private Double order;
}
