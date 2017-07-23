package ru.home.shop.service.impl;

import com.fasterxml.uuid.Generators;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.repo.PresentRepository;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.exception.ValidationException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PresentServiceImplTest {

    private Present getValidAddPresent() {
        Present present = new Present();
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        Candy candy1 = new Candy();
        candy1.setId(Generators.timeBasedGenerator().generate());
        candy1.setCount(2);

        Candy candy2 = new Candy();
        candy2.setId(Generators.timeBasedGenerator().generate());
        candy2.setCount(6);

        present.getCandies().add(candy1);
        present.getCandies().add(candy2);

        return present;
    }

    @Test
    public void add_validEntry_shouldSetNewId() {
        PresentRepository mock = mock(PresentRepository.class);
        Present present = getValidAddPresent();

        new PresentServiceImpl(mock).add(present);
        verify(mock).add(present);
        MatcherAssert.assertThat(present.getId(), notNullValue());
    }

    @Test
    public void add_nullEntry_shouldThrowException() {
        PresentRepository mock = mock(PresentRepository.class);

        try {
            new PresentServiceImpl(mock).add(null);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void add_notValidEntry_shouldThrowException() {
        PresentRepository mock = mock(PresentRepository.class);

        try {
            new PresentServiceImpl(mock).add(new Present());
            fail();
        } catch (ValidationException e) {
            assertFalse(e.getErrors().isEmpty());
        }
    }

    @Test
    public void edit_validEntry_shouldInvokeRepository() {
        Present present = getValidAddPresent();
        present.setId(Generators.timeBasedGenerator().generate());

        PresentRepository mock = mock(PresentRepository.class);
        when(mock.edit(present)).thenReturn(1);


        new PresentServiceImpl(mock).edit(present);
        verify(mock).edit(present);
    }

    @Test
    public void edit_nullEntry_shouldThrowException() {
        PresentRepository mock = mock(PresentRepository.class);

        try {
            new PresentServiceImpl(mock).edit(null);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void edit_notValidEntry_shouldThrowException() {
        PresentRepository mock = mock(PresentRepository.class);

        try {
            new PresentServiceImpl(mock).edit(new Present());
            fail();
        } catch (ValidationException e) {
            assertFalse(e.getErrors().isEmpty());
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void edit_nonexistentId_shouldThrowException() {
        PresentRepository mock = mock(PresentRepository.class);
        Present present = getValidAddPresent();
        present.setId(Generators.timeBasedGenerator().generate());

        when(mock.edit(present)).thenReturn(0);

        new PresentServiceImpl(mock).edit(present);
    }

    @Test
    public void remove_validId_shouldInvokeRepository() {
        UUID id = Generators.timeBasedGenerator().generate();
        PresentRepository mock = mock(PresentRepository.class);
        when(mock.remove(id)).thenReturn(1);

        new PresentServiceImpl(mock).remove(id);
        verify(mock).remove(id);
    }

    @Test
    public void remove_nonexistentId_shouldThrowException() {
        PresentRepository mock = mock(PresentRepository.class);
        when(mock.remove(Generators.timeBasedGenerator().generate())).thenReturn(0);

        try {
            new PresentServiceImpl(mock).remove(Generators.timeBasedGenerator().generate());
            fail();
        } catch (EntityNotFoundException ignored) {
        }
    }

    @Test
    public void list_shouldReturnSameAsRepository() {
        PresentRepository mock = mock(PresentRepository.class);
        Collection<Present> res = Collections.singletonList(getValidAddPresent());

        when(mock.findAll()).thenReturn(res);

        assertEquals(res, new PresentServiceImpl(mock).listView());
        verify(mock).findAll();
    }

    @Test
    public void find_validId_shouldInvokeRepository() {
        PresentRepository mock = mock(PresentRepository.class);
        Present present = getValidAddPresent();
        UUID id = Generators.timeBasedGenerator().generate();

        when(mock.findFull(id)).thenReturn(present);

        assertEquals(present, new PresentServiceImpl(mock).find(id));
        verify(mock).findFull(id);
    }
}