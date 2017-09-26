package ru.home.shop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.model.PresentItem;
import ru.home.shop.query.candy.CandyEntry;
import ru.home.shop.service.PresentService;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PresentQueryControllerIT {

    private final PresentService presentService = mock(PresentService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new PresentQueryController(presentService))
            .setControllerAdvice(new ErrorHandler())
            .build();

    private Present getPresent() {
        Present present = new Present();
        present.setId(newUUID());
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        PresentItem item1 = new PresentItem();
        item1.setCandy(new CandyEntry());
        item1.getCandy().setId(newUUID());
        item1.setCount(2);

        PresentItem item2 = new PresentItem();
        item2.setCandy(new CandyEntry());
        item2.getCandy().setId(newUUID());
        item2.setCount(6);

        present.getItems().add(item1);
        present.getItems().add(item2);

        return present;
    }

    @Test
    public void findExistentPresentShouldReturnIt() throws Exception {
        Present present = getPresent();
        doReturn(present).when(presentService).find(any());

        mockMvc.perform(get("/presents/{id}", newUUID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(present.getId().toString())))
                .andExpect(jsonPath("$.name", equalTo(present.getName())))
                .andExpect(jsonPath("$.price", equalTo(present.getPrice().doubleValue())))
                .andExpect(jsonPath("$.items[0].candy.id", equalTo(present.getItems().get(0).getCandy().getId().toString())))
                .andExpect(jsonPath("$.items[0].count", equalTo(present.getItems().get(0).getCount())))
                .andExpect(jsonPath("$.items[1].candy.id", equalTo(present.getItems().get(1).getCandy().getId().toString())))
                .andExpect(jsonPath("$.items[1].count", equalTo(present.getItems().get(1).getCount())));
    }

    @Test
    public void findNotExistentPresentReturn404() throws Exception {
        doReturn(null).when(presentService).find(any());

        mockMvc.perform(get("/presents/{id}", newUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listPresentShouldReturnArray() throws Exception {
        doReturn(asList(getPresent(), getPresent())).when(presentService).listView();

        mockMvc.perform(get("/presents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}