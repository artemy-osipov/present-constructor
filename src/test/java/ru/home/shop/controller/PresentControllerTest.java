package ru.home.shop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.home.shop.PresentsApplication;
import ru.home.shop.domain.bean.CandyBean;
import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.domain.bean.VersionBean;
import ru.home.shop.exception.ResourceNotFoundException;
import ru.home.shop.exception.ValidationException;
import ru.home.shop.service.PresentService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.home.shop.util.JsonUtils.fromJson;
import static ru.home.shop.util.JsonUtils.toJson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PresentsApplication.class)
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

    private void assertPresentEquals(PresentBean expected, PresentBean actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());

        assertCandiesEquals(expected.getCandies(), actual.getCandies());
    }

    private void assertCandiesEquals(Collection<CandyBean> expectedCol, Collection<CandyBean> actualCol) {
        List<CandyBean> expected = new ArrayList<>(expectedCol);
        List<CandyBean> actual = new ArrayList<>(actualCol);

        Comparator<CandyBean> comparator = Comparator.comparing(VersionBean::getId);
        expected.sort(comparator);
        actual.sort(comparator);

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getCount(), actual.get(i).getCount());
        }
    }

    @Test
    public void addPresentWithValidEntityShouldReturn200() throws Exception {
        PresentBean present = getPresent();
        Integer id = present.getId();
        PresentService mock = mock(PresentService.class);

        getMockMvc(mock).perform(post("/present")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(id)));
    }

    @Test
    public void addPresentWithNotValidEntityShouldReturn400() throws Exception {
        PresentBean present = getPresent();
        PresentService mock = mock(PresentService.class);
        doThrow(ValidationException.class).when(mock).add(any(PresentBean.class));

        getMockMvc(mock).perform(post("/present")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editPresentValidEntityShouldReturn200() throws Exception {
        PresentBean present = getPresent();
        PresentService mock = mock(PresentService.class);

        getMockMvc(mock).perform(put("/present/{id}", present.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().isOk());
    }

    @Test
    public void editPresentWithNotValidEntityShouldReturn400() throws Exception {
        PresentBean present = getPresent();
        PresentService mock = mock(PresentService.class);
        doThrow(ValidationException.class).when(mock).edit(any(PresentBean.class));

        getMockMvc(mock).perform(put("/present/{id}", present.getId())
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(toJson(present)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editPresentWithNonExistentIdShouldReturn404() throws Exception {
        PresentBean present = getPresent();
        PresentService mock = mock(PresentService.class);
        doThrow(ResourceNotFoundException.class).when(mock).edit(any(PresentBean.class));

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
        doThrow(ResourceNotFoundException.class).when(mock).remove(id);

        getMockMvc(mock).perform(delete("/present/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findPresentWithValidIdShouldReturnEntity() throws Exception {
        PresentBean origin = getPresent();
        PresentService mock = mock(PresentService.class);
        when(mock.find(origin.getId())).thenReturn(origin);

        String responseJson = getMockMvc(mock).perform(get("/present/{id}", origin.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        PresentBean response = fromJson(responseJson, PresentBean.class);

        assertPresentEquals(response, origin);
    }

    @Test
    public void findPresentWithNotValidIdShouldReturn400() throws Exception {
        PresentBean present = getPresent();
        PresentService mock = mock(PresentService.class);
        doThrow(ValidationException.class).when(mock).find(present.getId());

        getMockMvc(mock).perform(get("/present/{id}", present.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findPresentWithNonexistentIdShouldReturn404() throws Exception {
        PresentBean present = getPresent();
        PresentService mock = mock(PresentService.class);
        when(mock.find(present.getId())).thenReturn(null);

        getMockMvc(mock).perform(get("/present/{id}", present.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listPresentWithNotEmptyDBShouldReturnArray() throws Exception {
        PresentBean present1 = getPresent();
        PresentBean present2 = new PresentBean();
        present2.setId(2);
        present2.setName("name2");
        present2.setPrice(BigDecimal.valueOf(-1.441));
        present2.setCandies(present1.getCandies());
        PresentBean[] origin = {present1, present2};

        PresentService mock = mock(PresentService.class);
        when(mock.listView()).thenReturn(asList(origin));

        String responseJson = getMockMvc(mock).perform(get("/present").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        PresentBean[] response = fromJson(responseJson, PresentBean[].class);

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