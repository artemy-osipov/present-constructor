package ru.home.shop.service.impl;

import org.junit.Test;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.repo.CandyRepository;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.CandyService;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static ru.home.shop.utils.UuidUtils.newUUID;

public class CandyServiceImplTest {

    private final CandyRepository candyRepository = mock(CandyRepository.class);
    private final CandyService candyService = new CandyServiceImpl(candyRepository);
    private final Candy candy = new Candy();

    @Test
    public void editShouldInvokeRepository() {
        when(candyRepository.edit(candy)).thenReturn(1);

        candyService.edit(candy);

        verify(candyRepository).edit(candy);
    }

    @Test(expected = EntityNotFoundException.class)
    public void editNonExistentEntityShouldThrowException() {
        when(candyRepository.edit(candy)).thenReturn(0);

        candyService.edit(candy);
    }

    @Test
    public void removeShouldInvokeRepository() {
        UUID id = newUUID();
        when(candyRepository.remove(id)).thenReturn(1);

        candyService.remove(id);

        verify(candyRepository).remove(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeNonExistentEntityShouldThrowException() {
        when(candyRepository.remove(any())).thenReturn(0);

        candyService.remove(newUUID());
    }

    @Test
    public void listShouldReturnSameAsRepository() {
        Collection<Candy> res = Collections.singletonList(candy);
        when(candyRepository.list()).thenReturn(res);

        assertThat(candyService.list(), equalTo(res));
    }

    @Test
    public void findShouldInvokeRepository() {
        UUID id = newUUID();
        when(candyRepository.findById(id)).thenReturn(candy);


        assertThat(candyService.find(id), equalTo(candy));
    }
}