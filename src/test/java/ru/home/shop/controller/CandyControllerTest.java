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
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
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

    private void assertCandyEqual(CandyBean expected, CandyBean actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getFirm(), actual.getFirm());
        assertEquals(expected.getOrder(), actual.getOrder(), 0.001);
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    public void addCandyWithValidEntityShouldReturn200() throws Exception {
        CandyBean candy = getCandy();
        Integer id = candy.getId();
        CandyService mock = mock(CandyService.class);

        getMockMvc(mock).perform(post("/candy")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(id)));
    }

    @Test
    public void addCandyWithNotValidEntityShouldReturn400() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).add(any(CandyBean.class));

        getMockMvc(mock).perform(post("/candy")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editCandyWithValidEntityShouldReturn200() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);

        getMockMvc(mock).perform(put("/candy/{id}", candy.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isOk());
    }

    @Test
    public void editCandyWithNotValidEntityShouldReturn400() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).edit(any(CandyBean.class));

        getMockMvc(mock).perform(put("/candy/{id}", candy.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editCandyWithNonExistentIdShouldReturn404() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ResourceNotFoundException.class).when(mock).edit(any(CandyBean.class));

        getMockMvc(mock).perform(put("/candy/{id}", candy.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(candy)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeCandyWithValidIdShouldReturn200() throws Exception {
        int id = 1;
        CandyService mock = mock(CandyService.class);

        getMockMvc(mock).perform(delete("/candy/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void removeCandyWithNotValidIdShouldReturn400() throws Exception {
        int id = 1;
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).remove(id);

        getMockMvc(mock).perform(delete("/candy/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void removeCandyWithNonExistentIdShouldReturn404() throws Exception {
        int id = 1;
        CandyService mock = mock(CandyService.class);
        doThrow(ResourceNotFoundException.class).when(mock).remove(id);

        getMockMvc(mock).perform(delete("/candy/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findCandyWithValidIdShouldReturnEntity() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);
        when(mock.find(candy.getId())).thenReturn(candy);

        String responseJson = getMockMvc(mock).perform(get("/candy/{id}", candy.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        CandyBean response = fromJson(responseJson, CandyBean.class);

        assertCandyEqual(candy, response);
    }

    @Test
    public void findCandyWithNotValidIdShouldReturn400() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).find(candy.getId());

        getMockMvc(mock).perform(get("/candy/{id}", candy.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findCandyWithNonExistentIdShouldReturn404() throws Exception {
        CandyBean candy = getCandy();
        CandyService mock = mock(CandyService.class);
        when(mock.find(candy.getId())).thenReturn(null);

        getMockMvc(mock).perform(get("/candy/{id}", candy.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listCandyWithNotEmptyDBShouldReturnArray() throws Exception {
        CandyBean candy1 = getCandy();
        CandyBean candy2 = new CandyBean();
        candy2.setId(2);
        candy2.setName("name2");
        candy2.setFirm("firm2");
        candy2.setPrice(BigDecimal.valueOf(-1.441));
        candy2.setOrder(0.01);
        CandyBean[] origin = {candy1, candy2};

        CandyService mock = mock(CandyService.class);
        when(mock.list()).thenReturn(asList(origin));

        String responseJson = getMockMvc(mock).perform(get("/candy").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        CandyBean[] response = fromJson(responseJson, CandyBean[].class);

        assertEquals(origin.length, response.length);

        for (int i = 0; i < origin.length; i++) {
            assertCandyEqual(origin[i], response[i]);
        }
    }

    @Test
    public void listCandyWithEmptyDBShouldReturnEmptyArray() throws Exception {
        CandyService mock = mock(CandyService.class);
        when(mock.list()).thenReturn(new ArrayList<>());

        getMockMvc(mock).perform(get("/candy").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
        verify(mock).list();
    }
}