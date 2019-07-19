package ru.home.shop.api.candy;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Value
@RequiredArgsConstructor
public class UpdateCandyCommand {

    private UUID id;
    private String name;
    private String firm;
    private BigDecimal price;
    private Double order;
}
