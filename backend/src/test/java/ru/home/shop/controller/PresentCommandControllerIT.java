package ru.home.shop.controller;

import com.fasterxml.uuid.Generators;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.home.shop.controller.dto.AddPresentDTO;
import ru.home.shop.controller.dto.PresentItemDTO;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.PresentService;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.home.shop.utils.JsonUtils.toJson;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PresentCommandControllerIT {

    private final PresentService presentService = mock(PresentService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new PresentCommandController(presentService))
            .setControllerAdvice(new ErrorHandler())
            .build();

    private AddPresentDTO getUpdateDTO() {
        AddPresentDTO dto = new AddPresentDTO();
        dto.setName("name");
        dto.setPrice(BigDecimal.valueOf(4.2));

        PresentItemDTO item1 = new PresentItemDTO();
        item1.setCandyId(Generators.timeBasedGenerator().generate());
        item1.setCount(2);

        PresentItemDTO item2 = new PresentItemDTO();
        item2.setCandyId(Generators.timeBasedGenerator().generate());
        item2.setCount(6);

        dto.setItems(asList(item1, item2));

        return dto;
    }

    @Test
    public void addPresentWithValidEntityShouldReturnId() throws Exception {
        mockMvc.perform(post("/presents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void addPresentWithNotValidEntityShouldReturnErrors() throws Exception {
        mockMvc.perform(post("/presents")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors", hasSize(3)));
    }

    @Test
    public void removePresentShouldReturn200() throws Exception {
        mockMvc.perform(delete("/presents/{id}", newUUID()))
                .andExpect(status().isOk());
    }

    @Test
    public void removePresentWithNonexistentIdShouldReturn404() throws Exception {
        doThrow(EntityNotFoundException.class).when(presentService).remove(any());

        mockMvc.perform(delete("/presents/{id}", newUUID()))
                .andExpect(status().isNotFound());
    }
}