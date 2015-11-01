package ru.home.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PresentsApplication.class)
public class CandyControllerTest {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private MockMvc getMockMvc(CandyService mockService) {
        return MockMvcBuilders.standaloneSetup(new CandyController(mockService)).build();
    }

    private CandyBean getValidCandy() {
        CandyBean candy = new CandyBean();
        candy.setId(1);
        candy.setName("name");
        candy.setFirm("firm");
        candy.setPrice(BigDecimal.valueOf(4.20));
        candy.setOrder(51);

        return candy;
    }

    private String toJson(Object o) throws JsonProcessingException {
        return JSON_MAPPER.writeValueAsString(o);
    }

    @Test
    public void addCandy_validEntity_shouldReturn200() throws Exception {
        CandyBean candy = getValidCandy();
        Integer id = candy.getId();
        CandyService mock = mock(CandyService.class);
        getMockMvc(mock).perform(put("/candy")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(id)));
        verify(mock).add(candy);
    }

    @Test
    public void addCandy_notValidEntity_shouldReturn400() throws Exception {
        CandyBean candy = getValidCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).add(candy);
        getMockMvc(mock).perform(put("/candy")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().is(400));
        verify(mock).add(candy);
    }

    @Test
    public void editCandy_validEntity_shouldReturn200() throws Exception {
        CandyBean candy = getValidCandy();
        CandyService mock = mock(CandyService.class);
        getMockMvc(mock).perform(post("/candy/{id}", candy.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isOk());
        verify(mock).edit(candy);
    }

    @Test
    public void editCandy_notValidEntity_shouldReturn400() throws Exception {
        CandyBean candy = getValidCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).edit(candy);
        getMockMvc(mock).perform(post("/candy/{id}", candy.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().is(400));
        verify(mock).edit(candy);
    }

    @Test
    public void editCandy_nonexistentId_shouldReturn404() throws Exception {
        CandyBean candy = getValidCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ResourceNotFoundException.class).when(mock).edit(candy);
        getMockMvc(mock).perform(post("/candy/{id}", candy.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().is(404));
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
                .andExpect(status().is(400));
        verify(mock).remove(id);
    }

    @Test
    public void removeCandy_nonexistentId_shouldReturn404() throws Exception {
        int id = 1;
        CandyService mock = mock(CandyService.class);
        doThrow(ResourceNotFoundException.class).when(mock).remove(id);
        getMockMvc(mock).perform(delete("/candy/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
        verify(mock).remove(id);
    }

    @Test
    public void findCandy_validId_shouldReturnEntity() throws Exception {
        CandyBean candy = getValidCandy();
        CandyService mock = mock(CandyService.class);
        when(mock.find(candy.getId())).thenReturn(candy);
        getMockMvc(mock).perform(get("/candy/{id}", candy.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(candy.getId())))
                .andExpect(jsonPath("$.name", is(candy.getName())))
                .andExpect(jsonPath("$.firm", is(candy.getFirm())))
                .andExpect(jsonPath("$.price", is(candy.getPrice().doubleValue())))
                .andExpect(jsonPath("$.order", is(candy.getOrder())));
        verify(mock).find(candy.getId());
    }

    @Test
    public void findCandy_notValidId_shouldReturn400() throws Exception {
        CandyBean candy = getValidCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).find(candy.getId());
        getMockMvc(mock).perform(get("/candy/{id}", candy.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
        verify(mock).find(candy.getId());
    }

    @Test
    public void findCandy_nonexistentId_shouldReturn404() throws Exception {
        CandyBean candy = getValidCandy();
        CandyService mock = mock(CandyService.class);
        when(mock.find(candy.getId())).thenReturn(null);
        getMockMvc(mock).perform(get("/candy/{id}", candy.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
        verify(mock).find(candy.getId());
    }

    @Test
    public void listCandy_notEmptyDB_shouldReturnArray() throws Exception {
        CandyBean candy1 = getValidCandy();
        CandyBean candy2 = new CandyBean();
        candy2.setId(2);
        candy2.setName("name2");
        candy2.setFirm("firm2");
        candy2.setPrice(BigDecimal.valueOf(-1.441));
        candy2.setOrder(0.01);
        Collection<CandyBean> res = Arrays.asList(candy1, candy2);

        CandyService mock = mock(CandyService.class);
        when(mock.list()).thenReturn(res);
        getMockMvc(mock).perform(get("/candy").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(candy1.getId())))
                .andExpect(jsonPath("$[0].name", is(candy1.getName())))
                .andExpect(jsonPath("$[0].firm", is(candy1.getFirm())))
                .andExpect(jsonPath("$[0].price", is(candy1.getPrice().doubleValue())))
                .andExpect(jsonPath("$[0].order", is(candy1.getOrder())))
                .andExpect(jsonPath("$[1].id", is(candy2.getId())))
                .andExpect(jsonPath("$[1].name", is(candy2.getName())))
                .andExpect(jsonPath("$[1].firm", is(candy2.getFirm())))
                .andExpect(jsonPath("$[1].price", is(candy2.getPrice().doubleValue())))
                .andExpect(jsonPath("$[1].order", is(candy2.getOrder())));
        verify(mock).list();
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