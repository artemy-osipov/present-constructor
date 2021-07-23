package ru.home.shop.service.present;

import lombok.Getter;
import lombok.Value;
import lombok.experimental.Delegate;
import ru.home.shop.service.candy.CandyModel;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Present {

    @Delegate(excludes = Overridden.class)
    private final PresentModel presentModel;
    @Getter
    private final List<Item> items;

    public Present(PresentModel presentModel, Collection<CandyModel> candies) {
        this.presentModel = presentModel;
        this.items = buildItems(presentModel, candies);
    }

    private static List<Item> buildItems(PresentModel presentModel, Collection<CandyModel> candies) {
        Map<UUID, CandyModel> idToCandy = candies.stream()
                .collect(Collectors.toMap(
                        CandyModel::getId,
                        Function.identity()
                ));
        return presentModel.getItems()
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
        CandyModel candy;
        int count;
    }

    private interface Overridden {
        List<PresentModel.Item> getItems();
    }
}
