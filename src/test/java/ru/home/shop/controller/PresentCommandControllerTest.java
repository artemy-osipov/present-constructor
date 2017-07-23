package ru.home.shop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.home.shop.PresentsApplication;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.exception.ValidationException;
import ru.home.shop.service.PresentService;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.home.shop.util.JsonUtils.toJson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PresentsApplication.class)
public class PresentCommandControllerTest {

    private MockMvc getMockMvc(PresentService mockService) {
        return MockMvcBuilders.standaloneSetup(new PresentCommandController(mockService)).build();
    }

    private Present getPresent() {
        Present present = new Present();
        present.setId(1);
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        Candy candy1 = new Candy();
        candy1.setId(1);
        candy1.setCount(2);

        Candy candy2 = new Candy();
        candy2.setId(3);
        candy2.setCount(6);

        present.getCandies().add(candy1);
        present.getCandies().add(candy2);

        return present;
    }

    @Test
    public void addPresentWithValidEntityShouldReturn200() throws Exception {
        Present present = getPresent();
        Integer id = present.getId();
        PresentService mock = mock(PresentService.class);

        getMockMvc(mock).perform(post("/present")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(id)));
    }

    @Test
    public void addPresentWithNotValidEntityShouldReturn400() throws Exception {
        Present present = getPresent();
        PresentService mock = mock(PresentService.class);
        doThrow(ValidationException.class).when(mock).add(any(Present.class));

        getMockMvc(mock).perform(post("/present")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editPresentValidEntityShouldReturn200() throws Exception {
        Present present = getPresent();
        PresentService mock = mock(PresentService.class);

        getMockMvc(mock).perform(put("/present/{id}", present.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().isOk());
    }

    @Test
    public void editPresentWithNotValidEntityShouldReturn400() throws Exception {
        Present present = getPresent();
        PresentService mock = mock(PresentService.class);
        doThrow(ValidationException.class).when(mock).edit(any(Present.class));

        getMockMvc(mock).perform(put("/present/{id}", present.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editPresentWithNonExistentIdShouldReturn404() throws Exception {
        Present present = getPresent();
        PresentService mock = mock(PresentService.class);
        doThrow(EntityNotFoundException.class).when(mock).edit(any(Present.class));

        getMockMvc(mock).perform(put("/present/{id}", present.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removePresentWithValidIdShouldReturn200() throws Exception {
        int id = 1;
        PresentService mock = mock(PresentService.class);

        getMockMvc(mock).perform(delete("/present/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void removePresentWithNotValidIdShouldReturn400() throws Exception {
        int id = 1;
        PresentService mock = mock(PresentService.class);
        doThrow(ValidationException.class).when(mock).remove(id);

        getMockMvc(mock).perform(delete("/present/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void removePresentWithNonexistentIdShouldReturn404() throws Exception {
        int id = 1;
        PresentService mock = mock(PresentService.class);
        doThrow(EntityNotFoundException.class).when(mock).remove(id);

        getMockMvc(mock).perform(delete("/present/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}