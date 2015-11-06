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
import ru.home.shop.exception.ResourceNotFoundException;
import ru.home.shop.exception.ValidationException;
import ru.home.shop.service.CandyService;

import java.math.BigDecimal;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.home.shop.util.JsonUtils.fromJson;
import static ru.home.shop.util.JsonUtils.toJson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PresentsApplication.class)
public class CandyControllerTest {

    private MockMvc getMockMvc(CandyService mockService) {
        return MockMvcBuilders.standaloneSetup(new CandyController(mockService)).build();
    }

    private CandyBean getCandy() {
        CandyBean candy = new CandyBean();
        candy.setId(1);
        candy.setName("name");
        candy.setFirm("firm");
        candy.setPrice(BigDecimal.valueOf(4.20));
        candy.setOrder(51);

        return candy;
    }

    @Test
    public void addCandy_validEntity_shouldReturn200() throws Exception {
        CandyBean candy = getCandy();
        Integer id = candy.getId();
        CandyService mock = mock(CandyService.class);
        getMockMvc(mock).perform(post("/candy")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(id)));
        verify(mock).add(candy);
    }

    @Test
    public void addCandy_notValidEntity_shouldReturn400() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).add(candy);
        getMockMvc(mock).perform(post("/candy")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isBadRequest());
        verify(mock).add(candy);
    }

    @Test
    public void editCandy_validEntity_shouldReturn200() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);
        getMockMvc(mock).perform(put("/candy/{id}", candy.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isOk());
        verify(mock).edit(candy);
    }

    @Test
    public void editCandy_notValidEntity_shouldReturn400() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).edit(candy);
        getMockMvc(mock).perform(put("/candy/{id}", candy.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isBadRequest());
        verify(mock).edit(candy);
    }

    @Test
    public void editCandy_nonexistentId_shouldReturn404() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ResourceNotFoundException.class).when(mock).edit(candy);
        getMockMvc(mock).perform(put("/candy/{id}", candy.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isNotFound());
        verify(mock).edit(candy);
    }

    @Test
    public void removeCandy_validId_shouldReturn200() throws Exception {
        int id = 1;
        CandyService mock = mock(CandyService.class);
        getMockMvc(mock).perform(delete("/candy/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(mock).remove(id);
    }

    @Test
    public void removeCandy_notValidId_shouldReturn400() throws Exception {
        int id = 1;
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).remove(id);
        getMockMvc(mock).perform(delete("/candy/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(mock).remove(id);
    }

    @Test
    public void removeCandy_nonexistentId_shouldReturn404() throws Exception {
        int id = 1;
        CandyService mock = mock(CandyService.class);
        doThrow(ResourceNotFoundException.class).when(mock).remove(id);
        getMockMvc(mock).perform(delete("/candy/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(mock).remove(id);
    }

    @Test
    public void findCandy_validId_shouldReturnEntity() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);
        when(mock.find(candy.getId())).thenReturn(candy);
        String response = getMockMvc(mock).perform(get("/candy/{id}", candy.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        verify(mock).find(candy.getId());

        assertEquals(fromJson(response, CandyBean.class), candy);
    }

    @Test
    public void findCandy_notValidId_shouldReturn400() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).find(candy.getId());
        getMockMvc(mock).perform(get("/candy/{id}", candy.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(mock).find(candy.getId());
    }

    @Test
    public void findCandy_nonexistentId_shouldReturn404() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);
        when(mock.find(candy.getId())).thenReturn(null);
        getMockMvc(mock).perform(get("/candy/{id}", candy.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(mock).find(candy.getId());
    }

    @Test
    public void listCandy_notEmptyDB_shouldReturnArray() throws Exception {
        CandyBean candy1 = getCandy();
        CandyBean candy2 = new CandyBean();
        candy2.setId(2);
        candy2.setName("name2");
        candy2.setFirm("firm2");
        candy2.setPrice(BigDecimal.valueOf(-1.441));
        candy2.setOrder(0.01);
        CandyBean[] res = {candy1, candy2};

        CandyService mock = mock(CandyService.class);
        when(mock.list()).thenReturn(asList(res));
        String response = getMockMvc(mock).perform(get("/candy").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        verify(mock).list();

        assertArrayEquals(fromJson(response, CandyBean[].class), res);
    }

    @Test
    public void listCandy_emptyDB_shouldReturnEmptyArray() throws Exception {
        CandyService mock = mock(CandyService.class);
        when(mock.list()).thenReturn(new ArrayList<>());
        getMockMvc(mock).perform(get("/candy").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
        verify(mock).list();
    }
}