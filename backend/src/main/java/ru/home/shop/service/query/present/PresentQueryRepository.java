package ru.home.shop.service.query.present;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.home.shop.service.query.candy.CandyMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.home.db.Tables.*;

@Repository
@RequiredArgsConstructor
public class PresentQueryRepository {

    private final DSLContext dsl;
    private final CandyMapper candyMapper;
    private final PresentMapper presentMapper;

    public Collection<PresentQuery> list() {
        return dsl.selectFrom(PRESENT)
                .fetch(presentMapper);
    }

    public Optional<PresentQuery> findById(UUID id) {
        Optional<PresentQuery> present = dsl.selectFrom(PRESENT)
                .where(PRESENT.ID.eq(id))
                .fetchOptional(presentMapper);

        present.ifPresent(
                p -> p.setItems(listPresentItem(id))
        );

        return present;
    }

    private List<PresentItemQuery> listPresentItem(UUID present) {
        return dsl.select()
                .from(PRESENT_ITEM)
                .leftJoin(CANDY).on(PRESENT_ITEM.CANDY_ID.eq(CANDY.ID))
                .where(PRESENT_ITEM.PRESENT_ID.eq(present))
                .orderBy(CANDY.ORDER)
                .fetch()
                .map(r -> {
                    PresentItemQuery item = new PresentItemQuery();
                    item.setCandy(candyMapper.map(r.into(CANDY)));
                    item.setCount(r.getValue(PRESENT_ITEM.COUNT));

                    return item;
                });
    }
}
