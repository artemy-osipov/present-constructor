package ru.home.shop.controller;

import com.fasterxml.uuid.Generators;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.service.CandyService;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CandyQueryControllerIT {

    private final CandyService candyService = mock(CandyService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new CandyQueryController(candyService))
            .setControllerAdvice(new ErrorHandler())
            .build();

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
    public void findExistentCandyReturnIt() throws Exception {
        Candy candy = getCandy();
        doReturn(candy).when(candyService).find(any());

        mockMvc.perform(get("/candies/{id}", candy.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(candy.getId().toString())))
                .andExpect(jsonPath("$.name", equalTo(candy.getName())))
                .andExpect(jsonPath("$.firm", equalTo(candy.getFirm())))
                .andExpect(jsonPath("$.price", equalTo(candy.getPrice().doubleValue())))
                .andExpect(jsonPath("$.order", equalTo(candy.getOrder())));
    }

    @Test
    public void findNotExistentCandyShouldReturn404() throws Exception {
        doReturn(null).when(candyService).find(any());

        mockMvc.perform(get("/candies/{id}", newUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listCandyShouldReturnArray() throws Exception {
        doReturn(asList(getCandy(), getCandy())).when(candyService).list();

        mockMvc.perform(get("/candies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}