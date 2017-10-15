package ru.home.shop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.home.shop.query.candy.CandyEntry;
import ru.home.shop.query.candy.CandyEntryRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@WebMvcTest(CandyQueryController.class)
@WithMockUser
public class CandyQueryControllerIT {

    @MockBean
    private CandyEntryRepository repository;

    @Autowired
    private MockMvc mockMvc;

    private CandyEntry getCandy() {
        CandyEntry candy = new CandyEntry();
        candy.setId(newUUID());
        candy.setName("name");
        candy.setFirm("firm");
        candy.setPrice(BigDecimal.valueOf(4.20));
        candy.setOrder(51);

        return candy;
    }

    @Test
    public void findExistentCandyReturnIt() throws Exception {
        CandyEntry candy = getCandy();
        doReturn(Optional.of(candy)).when(repository).findById(any());

        mockMvc.perform(get("/candies/{id}", candy.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(candy.getId().toString())))
                .andExpect(jsonPath("$.name", equalTo(candy.getName())))
                .andExpect(jsonPath("$.firm", equalTo(candy.getFirm())))
                .andExpect(jsonPath("$.price", equalTo(candy.getPrice().doubleValue())))
                .andExpect(jsonPath("$.order", equalTo(candy.getOrder())));
    }

    @Test
    @WithAnonymousUser
    public void findWithAnonymousUserShouldReturn401() throws Exception {
        doReturn(Optional.empty()).when(repository).findById(any());

        mockMvc.perform(get("/candies/{id}", newUUID()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void findNotExistentCandyShouldReturn404() throws Exception {
        doReturn(Optional.empty()).when(repository).findById(any());

        mockMvc.perform(get("/candies/{id}", newUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listCandyShouldReturnArray() throws Exception {
        doReturn(asList(getCandy(), getCandy())).when(repository).list();

        mockMvc.perform(get("/candies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}