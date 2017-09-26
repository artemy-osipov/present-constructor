package ru.home.shop.service.impl;

import org.junit.Test;
import ru.home.shop.query.candy.CandyEntryRepository;
import ru.home.shop.query.candy.CandyEntry;
import ru.home.shop.service.CandyService;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static ru.home.shop.utils.UuidUtils.newUUID;

public class CandyServiceImplTest {

    private final CandyEntryRepository candyEntryRepository = mock(CandyEntryRepository.class);
    private final CandyService candyService = new CandyServiceImpl(candyEntryRepository);
    private final CandyEntry candy = new CandyEntry();

    @Test
    public void listShouldReturnSameAsRepository() {
        Collection<CandyEntry> res = Collections.singletonList(candy);
        when(candyEntryRepository.list()).thenReturn(res);

        assertThat(candyService.list(), equalTo(res));
    }

    @Test
    public void findShouldInvokeRepository() {
        UUID id = newUUID();
        when(candyEntryRepository.findById(id)).thenReturn(candy);


        assertThat(candyService.find(id), equalTo(candy));
    }
}