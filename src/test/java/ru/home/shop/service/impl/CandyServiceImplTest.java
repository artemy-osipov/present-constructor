package ru.home.shop.service.impl;

import com.fasterxml.uuid.Generators;
import org.junit.Test;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.repo.CandyRepository;
import ru.home.shop.exception.EntityNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CandyServiceImplTest {

    @Test
    public void edit_validEntry_shouldInvokeRepository() {
        Candy candy = new Candy();
        candy.setId(Generators.timeBasedGenerator().generate());

        CandyRepository mock = mock(CandyRepository.class);
        when(mock.edit(candy)).thenReturn(1);


        new CandyServiceImpl(mock).edit(candy);
        verify(mock).edit(candy);
    }

    @Test(expected = EntityNotFoundException.class)
    public void edit_nonexistentId_shouldThrowException() {
        CandyRepository mock = mock(CandyRepository.class);
        Candy candy = new Candy();
        candy.setId(Generators.timeBasedGenerator().generate());

        when(mock.edit(candy)).thenReturn(0);

        new CandyServiceImpl(mock).edit(candy);
    }

    @Test
    public void remove_validId_shouldInvokeRepository() {
        UUID id = Generators.timeBasedGenerator().generate();
        CandyRepository mock = mock(CandyRepository.class);
        when(mock.remove(id)).thenReturn(1);

        new CandyServiceImpl(mock).remove(id);
        verify(mock).remove(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void remove_nonexistentId_shouldThrowException() {
        CandyRepository mock = mock(CandyRepository.class);
        when(mock.remove(Generators.timeBasedGenerator().generate())).thenReturn(0);

        new CandyServiceImpl(mock).remove(Generators.timeBasedGenerator().generate());
    }

    @Test
    public void list_shouldReturnSameAsRepository() {
        CandyRepository mock = mock(CandyRepository.class);
        Collection<Candy> res = Collections.singletonList(new Candy());

        when(mock.findAll()).thenReturn(res);

        assertEquals(res, new CandyServiceImpl(mock).list());
        verify(mock).findAll();
    }

    @Test
    public void find_validId_shouldInvokeRepository() {
        CandyRepository mock = mock(CandyRepository.class);
        Candy candy = new Candy();
        UUID id = Generators.timeBasedGenerator().generate();

        when(mock.find(id)).thenReturn(candy);

        assertEquals(candy, new CandyServiceImpl(mock).find(id));
        verify(mock).find(id);
    }
}