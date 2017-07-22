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
import ru.home.shop.exception.ValidationException;
import ru.home.shop.service.CandyService;

import java.math.BigDecimal;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.home.shop.util.JsonUtils.fromJson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PresentsApplication.class)
public class CandyQueryControllerTest {

    private MockMvc getMockMvc(CandyService mockService) {
        return MockMvcBuilders.standaloneSetup(new CandyQueryController(mockService)).build();
    }

    private Candy getCandy() {
        Candy candy = new Candy();
        candy.setId(1);
        candy.setName("name");
        candy.setFirm("firm");
        candy.setPrice(BigDecimal.valueOf(4.20));
        candy.setOrder(51);

        return candy;
    }

    private void assertCandyEqual(Candy expected, Candy actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getFirm(), actual.getFirm());
        assertEquals(expected.getOrder(), actual.getOrder(), 0.001);
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    public void findCandyWithValidIdShouldReturnEntity() throws Exception {
        Candy candy = getCandy();
        CandyService mock = mock(CandyService.class);
        when(mock.find(candy.getId())).thenReturn(candy);

        String responseJson = getMockMvc(mock).perform(get("/candy/{id}", candy.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        Candy response = fromJson(responseJson, Candy.class);

        assertCandyEqual(candy, response);
    }

    @Test
    public void findCandyWithNotValidIdShouldReturn400() throws Exception {
        Candy candy = getCandy();
        CandyService mock = mock(CandyService.class);
        doThrow(ValidationException.class).when(mock).find(candy.getId());

        getMockMvc(mock).perform(get("/candy/{id}", candy.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findCandyWithNonExistentIdShouldReturn404() throws Exception {
        Candy candy = getCandy();
        CandyService mock = mock(CandyService.class);
        when(mock.find(candy.getId())).thenReturn(null);

        getMockMvc(mock).perform(get("/candy/{id}", candy.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listCandyWithNotEmptyDBShouldReturnArray() throws Exception {
        Candy candy1 = getCandy();
        Candy candy2 = new Candy();
        candy2.setId(2);
        candy2.setName("name2");
        candy2.setFirm("firm2");
        candy2.setPrice(BigDecimal.valueOf(-1.441));
        candy2.setOrder(0.01);
        Candy[] origin = {candy1, candy2};

        CandyService mock = mock(CandyService.class);
        when(mock.list()).thenReturn(asList(origin));

        String responseJson = getMockMvc(mock).perform(get("/candy").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        Candy[] response = fromJson(responseJson, Candy[].class);

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