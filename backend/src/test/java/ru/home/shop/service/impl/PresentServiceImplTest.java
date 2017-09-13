package ru.home.shop.service.impl;

import org.junit.Test;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.repo.PresentRepository;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.PresentService;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static ru.home.shop.utils.UuidUtils.newUUID;

public class PresentServiceImplTest {
    
    private final PresentRepository presentRepository = mock(PresentRepository.class);
    private final PresentService presentService = new PresentServiceImpl(presentRepository);
    private final Present present = new Present();

    @Test
    public void removeShouldInvokeRepository() {
        UUID id = newUUID();
        when(presentRepository.remove(id)).thenReturn(1);

        presentService.remove(id);

        verify(presentRepository).remove(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeNonExistentEntityShouldThrowException() {
        when(presentRepository.remove(any())).thenReturn(0);

        presentService.remove(newUUID());
    }

    @Test
    public void listShouldReturnSameAsRepository() {
        Collection<Present> res = Collections.singletonList(present);
        when(presentRepository.listView()).thenReturn(res);

        assertThat(presentService.listView(), equalTo(res));
    }

    @Test
    public void findShouldInvokeRepository() {
        UUID id = newUUID();
        when(presentRepository.findById(id)).thenReturn(present);

        assertThat(presentService.find(id), equalTo(present));
    }
}