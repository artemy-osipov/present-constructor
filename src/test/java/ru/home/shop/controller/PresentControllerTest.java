package ru.home.shop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.home.shop.PresentsApplication;
import ru.home.shop.domain.bean.CandyBean;
import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.exception.ResourceNotFoundException;
import ru.home.shop.exception.ValidationException;
import ru.home.shop.service.PresentService;

import java.math.BigDecimal;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.home.shop.util.JsonUtils.fromJson;
import static ru.home.shop.util.JsonUtils.toJson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PresentsApplication.class)
public class PresentControllerTest {

    private MockMvc getMockMvc(PresentService mockService) {
        return MockMvcBuilders.standaloneSetup(new PresentController(mockService)).build();
    }

    private PresentBean getPresent() {
        PresentBean present = new PresentBean();
        present.setId(1);
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        CandyBean candy1 = new CandyBean();
        candy1.setId(1);
        candy1.setCount(2);

        CandyBean candy2 = new CandyBean();
        candy2.setId(3);
        candy2.setCount(6);

        present.getCandies().add(candy1);
        present.getCandies().add(candy2);

        return present;
    }

    @Test
    public void addPresent_validEntity_shouldReturn200() throws Exception {
        PresentBean present = getPresent();
        Integer id = present.getId();
        PresentService mock = mock(PresentService.class);
        getMockMvc(mock).perform(put("/present")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(id)));
        verify(mock).add(present);
    }

    @Test
    public void addPresent_notValidEntity_shouldReturn400() throws Exception {
        PresentBean present = getPresent();
        PresentService mock = mock(PresentService.class);
        doThrow(ValidationException.class).when(mock).add(present);
        getMockMvc(mock).perform(put("/present")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().is(400));
        verify(mock).add(present);
    }

    @Test
    public void editPresent_validEntity_shouldReturn200() throws Exception {
        PresentBean present = getPresent();
        PresentService mock = mock(PresentService.class);
        getMockMvc(mock).perform(post("/present/{id}", present.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().isOk());
        verify(mock).edit(present);
    }

    @Test
    public void editPresent_notValidEntity_shouldReturn400() throws Exception {
        PresentBean present = getPresent();
        PresentService mock = mock(PresentService.class);
        doThrow(ValidationException.class).when(mock).edit(present);
        getMockMvc(mock).perform(post("/present/{id}", present.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().is(400));
        verify(mock).edit(present);
    }

    @Test
    public void editPresent_nonexistentId_shouldReturn404() throws Exception {
        PresentBean present = getPresent();
        PresentService mock = mock(PresentService.class);
        doThrow(ResourceNotFoundException.class).when(mock).edit(present);
        getMockMvc(mock).perform(post("/present/{id}", present.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().is(404));
        verify(mock).edit(present);
    }

    @Test
    public void removePresent_validId_shouldReturn200() throws Exception {
        int id = 1;
        PresentService mock = mock(PresentService.class);
        getMockMvc(mock).perform(delete("/present/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(mock).remove(id);
    }

    @Test
    public void removePresent_notValidId_shouldReturn400() throws Exception {
        int id = 1;
        PresentService mock = mock(PresentService.class);
        doThrow(ValidationException.class).when(mock).remove(id);
        getMockMvc(mock).perform(delete("/present/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
        verify(mock).remove(id);
    }

    @Test
    public void removePresent_nonexistentId_shouldReturn404() throws Exception {
        int id = 1;
        PresentService mock = mock(PresentService.class);
        doThrow(ResourceNotFoundException.class).when(mock).remove(id);
        getMockMvc(mock).perform(delete("/present/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
        verify(mock).remove(id);
    }

    @Test
    public void findPresent_validId_shouldReturnEntity() throws Exception {
        PresentBean present = getPresent();
        PresentService mock = mock(PresentService.class);
        when(mock.find(present.getId())).thenReturn(present);

        String response = getMockMvc(mock).perform(get("/present/{id}", present.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        verify(mock).find(present.getId());

        assertEquals(fromJson(response, PresentBean.class), present);
    }

    @Test
    public void findPresent_notValidId_shouldReturn400() throws Exception {
        PresentBean present = getPresent();
        PresentService mock = mock(PresentService.class);
        doThrow(ValidationException.class).when(mock).find(present.getId());
        getMockMvc(mock).perform(get("/present/{id}", present.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
        verify(mock).find(present.getId());
    }

    @Test
    public void findPresent_nonexistentId_shouldReturn404() throws Exception {
        PresentBean present = getPresent();
        PresentService mock = mock(PresentService.class);
        when(mock.find(present.getId())).thenReturn(null);
        getMockMvc(mock).perform(get("/present/{id}", present.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
        verify(mock).find(present.getId());
    }

    @Test
    public void listPresent_notEmptyDB_shouldReturnArray() throws Exception {
        PresentBean present1 = getPresent();
        PresentBean present2 = new PresentBean();
        present2.setId(2);
        present2.setName("name2");
        present2.setPrice(BigDecimal.valueOf(-1.441));
        present2.setCandies(present1.getCandies());
        PresentBean[] res = {present1, present2};

        PresentService mock = mock(PresentService.class);
        when(mock.listView()).thenReturn(asList(res));
        String response = getMockMvc(mock).perform(get("/present").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        verify(mock).listView();

        assertArrayEquals(fromJson(response, PresentBean[].class), res);
    }

    @Test
    public void listPresent_emptyDB_shouldReturnEmptyArray() throws Exception {
        PresentService mock = mock(PresentService.class);
        when(mock.listView()).thenReturn(new ArrayList<>());
        getMockMvc(mock).perform(get("/present").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
        verify(mock).listView();
    }
}