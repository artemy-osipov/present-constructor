package ru.home.shop.domain.repo.jooq;

import com.fasterxml.uuid.Generators;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.repo.PresentRepository;

import java.util.Collection;
import java.util.UUID;

import static ru.home.db.Tables.*;

@Repository
public class JOOQPresentRepository implements PresentRepository {

    private final DSLContext dsl;

    @Autowired
    public JOOQPresentRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    @Transactional
    public void add(Present present) {
        dsl.insertInto(PRESENT)
                .set(PRESENT.ID, present.getId())
                .set(PRESENT.NAME, present.getName())
                .set(PRESENT.PRICE, present.getPrice())
                .execute();

        addCandiesToPresent(present.getId(), present.getCandies());
    }

    private void addCandiesToPresent(UUID present, Collection<Candy> candies) {
        candies.forEach(
                candy -> dsl.insertInto(PRESENT_ITEM)
                        .set(PRESENT_ITEM.ID, Generators.timeBasedGenerator().generate())
                        .set(PRESENT_ITEM.PRESENT, present)
                        .set(PRESENT_ITEM.CANDY, candy.getId())
                        .set(PRESENT_ITEM.COUNT, candy.getCount())
                        .execute()
        );
    }

    @Override
    public int remove(UUID id) {
        return dsl.deleteFrom(PRESENT)
                .where(PRESENT.ID.eq(id))
                .execute();
    }

    @Override
    @Transactional
    public int edit(Present present) {
        int updated = dsl.update(PRESENT)
                .set(PRESENT.NAME, present.getName())
                .set(PRESENT.PRICE, present.getPrice())
                .where(PRESENT.ID.eq(present.getId()))
                .execute();

        dsl.deleteFrom(PRESENT_ITEM)
                .where(PRESENT_ITEM.PRESENT.eq(present.getId()));

        addCandiesToPresent(present.getId(), present.getCandies());

        return updated;
    }

    @Override
    public Collection<Present> findAll() {
        return dsl.selectFrom(PRESENT)
                .fetch(new PresentMapper());
    }

    @Override
    @Transactional
    public Present findFull(UUID id) {
        Present present = dsl.selectFrom(PRESENT)
                .where(PRESENT.ID.eq(id))
                .fetchOne(new PresentMapper());

        if (present != null) {
            present.setCandies(listCandiesByPresent(id));
        }

        return present;
    }

    private Collection<Candy> listCandiesByPresent(UUID present) {
        return dsl.select()
                .from(PRESENT_ITEM)
                .leftJoin(CANDY).on(PRESENT_ITEM.CANDY.eq(CANDY.ID))
                .where(PRESENT_ITEM.PRESENT.eq(present))
                .orderBy(CANDY.ORDER)
                .fetch()
                .map(r -> {
                    Candy candy = new Candy();
                    candy.setId(r.getValue(PRESENT_ITEM.CANDY));
                    candy.setName(r.getValue(CANDY.NAME));
                    candy.setFirm(r.getValue(CANDY.FIRM));
                    candy.setPrice(r.getValue(CANDY.PRICE));
                    candy.setOrder(r.getValue(CANDY.ORDER));
                    candy.setCount(r.getValue(PRESENT_ITEM.COUNT));

                    return candy;
                });
    }
}
