package ru.home.shop.controller;

import com.fasterxml.uuid.Generators;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.home.shop.PresentsApplication;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.exception.ValidationException;
import ru.home.shop.service.CandyService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.home.shop.util.JsonUtils.toJson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PresentsApplication.class)
public class CandyCommandControllerTest {

    private MockMvc getMockMvc(CandyService mockService) {
        return MockMvcBuilders.standaloneSetup(new CandyCommandController(mockService)).build();
    }

    private Candy getCandy() {
        Candy candy = new Candy();
        candy.setId(Generators.timeBasedGenerator().generate());
        candy.setName("name");
        candy.setFirm("firm");
        candy.setPrice(BigDecimal.valueOf(4.20));
        candy.setOrder(51);

        return candy;
    }

    @Test
    public void addCandyWithValidEntityShouldReturn200() throws Exception {
        Candy candy = getCandy();
        String id = candy.getId().toString();
        CandyService mock = mock(CandyService.class);

        getMockMvc(mock).perform(post("/candy")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(id)));
    }

    @Test
    public void addCandyWithNotValidEntityShouldReturn400() throws Exception {
        Candy candy = getCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).add(any(Candy.class));

        getMockMvc(mock).perform(post("/candy")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editCandyWithValidEntityShouldReturn200() throws Exception {
        Candy candy = getCandy();
        CandyService mock = mock(CandyService.class);

        getMockMvc(mock).perform(put("/candy/{id}", candy.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isOk());
    }

    @Test
    public void editCandyWithNotValidEntityShouldReturn400() throws Exception {
        Candy candy = getCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).edit(any(Candy.class));

        getMockMvc(mock).perform(put("/candy/{id}", candy.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editCandyWithNonExistentIdShouldReturn404() throws Exception {
        Candy candy = getCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(EntityNotFoundException.class).when(mock).edit(any(Candy.class));

        getMockMvc(mock).perform(put("/candy/{id}", candy.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeCandyWithValidIdShouldReturn200() throws Exception {
        UUID id = Generators.timeBasedGenerator().generate();
        CandyService mock = mock(CandyService.class);

        getMockMvc(mock).perform(delete("/candy/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void removeCandyWithNotValidIdShouldReturn400() throws Exception {
        UUID id = Generators.timeBasedGenerator().generate();
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).remove(id);

        getMockMvc(mock).perform(delete("/candy/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void removeCandyWithNonExistentIdShouldReturn404() throws Exception {
        UUID id = Generators.timeBasedGenerator().generate();
        CandyService mock = mock(CandyService.class);
        doThrow(EntityNotFoundException.class).when(mock).remove(id);

        getMockMvc(mock).perform(delete("/candy/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}