package ru.home.shop.service.impl;

import org.junit.Test;
import ru.home.shop.domain.bean.CandyBean;
import ru.home.shop.domain.repo.jooq.JOOQCandyRepository;
import ru.home.shop.exception.ValidationException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CandyServiceImplTest {

    private CandyBean getValidAddCandy() {
        CandyBean candy = new CandyBean();
        candy.setName("name");
        candy.setFirm("firm");
        candy.setPrice(BigDecimal.valueOf(4.2));
        candy.setOrder(51);

        return candy;
    }

    @Test
    public void add_validEntry_shouldInvokeRepository() {
        JOOQCandyRepository mock = mock(JOOQCandyRepository.class);
        CandyBean candy = getValidAddCandy();

        new CandyServiceImpl(mock).add(candy);
        verify(mock).add(candy);
    }

    @Test
    public void add_nullEntry_shouldThrowException() {
        JOOQCandyRepository mock = mock(JOOQCandyRepository.class);

        try {
            new CandyServiceImpl(mock).add(null);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void add_notValidEntry_shouldThrowException() {
        JOOQCandyRepository mock = mock(JOOQCandyRepository.class);

        try {
            new CandyServiceImpl(mock).add(new CandyBean());
            fail();
        } catch (ValidationException e) {
            assertFalse(e.getErrors().isEmpty());
        }
    }

    @Test
    public void edit_validEntry_shouldInvokeRepository() {
        JOOQCandyRepository mock = mock(JOOQCandyRepository.class);
        CandyBean candy = getValidAddCandy();
        candy.setId(1);

        new CandyServiceImpl(mock).edit(candy);
        verify(mock).edit(candy);
    }

    @Test
    public void edit_nullEntry_shouldThrowException() {
        JOOQCandyRepository mock = mock(JOOQCandyRepository.class);

        try {
            new CandyServiceImpl(mock).edit(null);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void edit_notValidEntry_shouldThrowException() {
        JOOQCandyRepository mock = mock(JOOQCandyRepository.class);

        try {
            new CandyServiceImpl(mock).edit(new CandyBean());
            fail();
        } catch (ValidationException e) {
            assertFalse(e.getErrors().isEmpty());
        }
    }

    @Test
    public void remove_validId_shouldInvokeRepository() {
        JOOQCandyRepository mock = mock(JOOQCandyRepository.class);
        int id = 1;

        new CandyServiceImpl(mock).remove(id);
        verify(mock).remove(id);
    }

    @Test
    public void remove_notValidId_shouldThrowException() {
        JOOQCandyRepository mock = mock(JOOQCandyRepository.class);

        try {
            new CandyServiceImpl(mock).remove(-1);
            fail();
        } catch (ValidationException e) {
            assertEquals(1, e.getErrors().size());
            assertEquals("incorrect", e.getErrors().get("id"));
        }
    }

    @Test
    public void list_shouldReturnSameAsRepository() {
        JOOQCandyRepository mock = mock(JOOQCandyRepository.class);
        Collection<CandyBean> res = Collections.singletonList(getValidAddCandy());

        when(mock.findAll()).thenReturn(res);

        assertEquals(res, new CandyServiceImpl(mock).list());
        verify(mock).findAll();
    }

    @Test
    public void find_validId_shouldInvokeRepository() {
        JOOQCandyRepository mock = mock(JOOQCandyRepository.class);
        CandyBean candy = getValidAddCandy();
        int id = 1;

        when(mock.find(id)).thenReturn(candy);

        assertEquals(candy, new CandyServiceImpl(mock).find(id));
        verify(mock).find(id);
    }

    @Test
    public void find_notValidId_shouldThrowException() {
        JOOQCandyRepository mock = mock(JOOQCandyRepository.class);

        try {
            new CandyServiceImpl(mock).find(0);
            fail();
        } catch (ValidationException e) {
            assertEquals(1, e.getErrors().size());
            assertEquals("incorrect", e.getErrors().get("id"));
        }
    }
}