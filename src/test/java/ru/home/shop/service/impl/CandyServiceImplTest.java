package ru.home.shop.service.impl;

import org.junit.Test;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.repo.CandyRepository;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.exception.ValidationException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CandyServiceImplTest {

    private Candy getValidAddCandy() {
        Candy candy = new Candy();
        candy.setName("name");
        candy.setFirm("firm");
        candy.setPrice(BigDecimal.valueOf(4.2));
        candy.setOrder(51);

        return candy;
    }

    @Test
    public void add_validEntry_shouldSetNewId() {
        CandyRepository mock = mock(CandyRepository.class);
        Candy candy = getValidAddCandy();

        Integer newId = 2;

        when(mock.add(candy)).thenReturn(newId);

        new CandyServiceImpl(mock).add(candy);
        verify(mock).add(candy);
        assertEquals(newId, candy.getId());
    }

    @Test
    public void add_nullEntry_shouldThrowException() {
        CandyRepository mock = mock(CandyRepository.class);

        try {
            new CandyServiceImpl(mock).add(null);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void add_notValidEntry_shouldThrowException() {
        CandyRepository mock = mock(CandyRepository.class);

        try {
            new CandyServiceImpl(mock).add(new Candy());
            fail();
        } catch (ValidationException e) {
            assertFalse(e.getErrors().isEmpty());
        }
    }

    @Test
    public void edit_validEntry_shouldInvokeRepository() {
        Candy candy = getValidAddCandy();
        candy.setId(1);

        CandyRepository mock = mock(CandyRepository.class);
        when(mock.edit(candy)).thenReturn(1);


        new CandyServiceImpl(mock).edit(candy);
        verify(mock).edit(candy);
    }

    @Test
    public void edit_nullEntry_shouldThrowException() {
        CandyRepository mock = mock(CandyRepository.class);

        try {
            new CandyServiceImpl(mock).edit(null);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void edit_notValidEntry_shouldThrowException() {
        CandyRepository mock = mock(CandyRepository.class);

        try {
            new CandyServiceImpl(mock).edit(new Candy());
            fail();
        } catch (ValidationException e) {
            assertFalse(e.getErrors().isEmpty());
        }
    }

    @Test
    public void edit_nonexistentId_shouldThrowException() {
        CandyRepository mock = mock(CandyRepository.class);
        Candy candy = getValidAddCandy();
        candy.setId(1);

        when(mock.edit(candy)).thenReturn(0);

        try {
            new CandyServiceImpl(mock).edit(candy);
            fail();
        } catch (EntityNotFoundException ignored) {
        }
    }

    @Test
    public void remove_validId_shouldInvokeRepository() {
        int id = 1;
        CandyRepository mock = mock(CandyRepository.class);
        when(mock.remove(id)).thenReturn(1);

        new CandyServiceImpl(mock).remove(id);
        verify(mock).remove(id);
    }

    @Test
    public void remove_nonexistentId_shouldThrowException() {
        CandyRepository mock = mock(CandyRepository.class);
        when(mock.remove(1)).thenReturn(0);

        try {
            new CandyServiceImpl(mock).remove(1);
            fail();
        } catch (EntityNotFoundException ignored) {
        }
    }

    @Test
    public void list_shouldReturnSameAsRepository() {
        CandyRepository mock = mock(CandyRepository.class);
        Collection<Candy> res = Collections.singletonList(getValidAddCandy());

        when(mock.findAll()).thenReturn(res);

        assertEquals(res, new CandyServiceImpl(mock).list());
        verify(mock).findAll();
    }

    @Test
    public void find_validId_shouldInvokeRepository() {
        CandyRepository mock = mock(CandyRepository.class);
        Candy candy = getValidAddCandy();
        int id = 1;

        when(mock.find(id)).thenReturn(candy);

        assertEquals(candy, new CandyServiceImpl(mock).find(id));
        verify(mock).find(id);
    }
}