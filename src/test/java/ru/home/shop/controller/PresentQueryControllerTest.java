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
import ru.home.shop.domain.model.Entity;
import ru.home.shop.domain.model.Present;
import ru.home.shop.exception.ValidationException;
import ru.home.shop.service.PresentService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.home.shop.util.JsonUtils.fromJson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PresentsApplication.class)
public class PresentQueryControllerTest {

    private MockMvc getMockMvc(PresentService mockService) {
        return MockMvcBuilders.standaloneSetup(new PresentQueryController(mockService)).build();
    }

    private Present getPresent() {
        Present present = new Present();
        present.setId(Generators.timeBasedGenerator().generate());
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

    private void assertPresentEquals(Present expected, Present actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());

        assertCandiesEquals(expected.getCandies(), actual.getCandies());
    }

    private void assertCandiesEquals(Collection<Candy> expectedCol, Collection<Candy> actualCol) {
        List<Candy> expected = new ArrayList<>(expectedCol);
        List<Candy> actual = new ArrayList<>(actualCol);

        Comparator<Candy> comparator = Comparator.comparing(Entity::getId);
        expected.sort(comparator);
        actual.sort(comparator);

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getCount(), actual.get(i).getCount());
        }
    }

    @Test
    public void findPresentWithValidIdShouldReturnEntity() throws Exception {
        Present origin = getPresent();
        PresentService mock = mock(PresentService.class);
        when(mock.find(origin.getId())).thenReturn(origin);

        String responseJson = getMockMvc(mock).perform(get("/present/{id}", origin.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        Present response = fromJson(responseJson, Present.class);

        assertPresentEquals(response, origin);
    }

    @Test
    public void findPresentWithNotValidIdShouldReturn400() throws Exception {
        Present present = getPresent();
        PresentService mock = mock(PresentService.class);
        doThrow(ValidationException.class).when(mock).find(present.getId());

        getMockMvc(mock).perform(get("/present/{id}", present.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findPresentWithNonexistentIdShouldReturn404() throws Exception {
        Present present = getPresent();
        PresentService mock = mock(PresentService.class);
        when(mock.find(present.getId())).thenReturn(null);

        getMockMvc(mock).perform(get("/present/{id}", present.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listPresentWithNotEmptyDBShouldReturnArray() throws Exception {
        Present present1 = getPresent();
        Present present2 = new Present();
        present2.setId(Generators.timeBasedGenerator().generate());
        present2.setName("name2");
        present2.setPrice(BigDecimal.valueOf(-1.441));
        present2.setCandies(present1.getCandies());
        Present[] origin = {present1, present2};

        PresentService mock = mock(PresentService.class);
        when(mock.listView()).thenReturn(asList(origin));

        String responseJson = getMockMvc(mock).perform(get("/present").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        Present[] response = fromJson(responseJson, Present[].class);

        assertEquals(origin.length, response.length);

        for (int i = 0; i < origin.length; i++) {
            assertPresentEquals(origin[i], response[i]);
        }
    }

    @Test
    public void listPresentWithEmptyDBShouldReturnEmptyArray() throws Exception {
        PresentService mock = mock(PresentService.class);
        when(mock.listView()).thenReturn(new ArrayList<>());

        getMockMvc(mock).perform(get("/present").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}