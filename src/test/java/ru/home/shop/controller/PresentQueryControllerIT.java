package ru.home.shop.controller;

import com.fasterxml.uuid.Generators;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;
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
        present.setId(Generators.timeBasedGenerator().generate());
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        Candy candy1 = new Candy();
        candy1.setId(Generators.timeBasedGenerator().generate());
        candy1.setCount(2);

        Candy candy2 = new Candy();
        candy2.setId(Generators.timeBasedGenerator().generate());
        candy2.setCount(6);

        present.getItems().add(candy1);
        present.getItems().add(candy2);

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
                .andExpect(jsonPath("$.items[0].id", equalTo(present.getItems().get(0).getId().toString())))
                .andExpect(jsonPath("$.items[0].count", equalTo(present.getItems().get(0).getCount())))
                .andExpect(jsonPath("$.items[1].id", equalTo(present.getItems().get(1).getId().toString())))
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