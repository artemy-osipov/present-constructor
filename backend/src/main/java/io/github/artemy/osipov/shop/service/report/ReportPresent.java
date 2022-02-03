package io.github.artemy.osipov.shop.service.report;

import io.github.artemy.osipov.shop.service.candy.Candy;
import io.github.artemy.osipov.shop.service.present.Present;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Value
public class ReportPresent {

    String name;
    BigDecimal price;
    List<Item> items;

    public ReportPresent(Present present, Collection<Candy> candies) {
        this.name = present.getName();
        this.price = present.getPrice();
        this.items = buildItems(present, candies);
    }

    private static List<Item> buildItems(Present present, Collection<Candy> candies) {
        Map<UUID, Candy> idToCandy = candies.stream()
                .collect(Collectors.toMap(
                        Candy::getId,
                        Function.identity()
                ));
        return present.getItems()
                .stream()
                .map(i -> new Item(
                        Objects.requireNonNull(
                                idToCandy.get(i.getCandyId())
                        ),
                        i.getCount()
                ))
                .sorted(Comparator.comparing((Item i) -> i.getCandy().getOrder()))
                .collect(Collectors.toList());
    }

    public BigDecimal computeCost() {
        return items.stream()
                .map(item -> item.getCandy().getPrice().multiply(BigDecimal.valueOf(item.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Value
    public static class Item {
        Candy candy;
        int count;
    }
}
