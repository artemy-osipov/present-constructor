package io.github.artemy.osipov.shop.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class DError {
    private String internalMessage;
    private Map<String, String> details = Map.of();

    public DError(String internalMessage) {
        this.internalMessage = internalMessage;
    }
}
