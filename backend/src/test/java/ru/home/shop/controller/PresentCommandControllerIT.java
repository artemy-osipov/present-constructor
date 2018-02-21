package ru.home.shop.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.home.shop.api.present.RemovePresentCommand;
import ru.home.shop.controller.dto.AddPresentDTO;
import ru.home.shop.controller.dto.EntityDTO;
import ru.home.shop.controller.dto.PresentItemDTO;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.command.present.PresentCommandHandler;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.home.shop.utils.JsonUtils.toJson;
import static ru.home.shop.utils.UuidUtils.newUUID;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PresentCommandController.class)
class PresentCommandControllerIT {

    @MockBean
    private PresentCommandHandler commandHandler;

    @Autowired
    private MockMvc mockMvc;

    private AddPresentDTO getUpdateDTO() {
        AddPresentDTO dto = new AddPresentDTO();
        dto.setName("name");
        dto.setPrice(BigDecimal.valueOf(4.2));

        PresentItemDTO item1 = new PresentItemDTO();
        item1.setCandy(new EntityDTO());
        item1.getCandy().setId(newUUID());
        item1.setCount(2);

        PresentItemDTO item2 = new PresentItemDTO();
        item2.setCandy(new EntityDTO());
        item2.getCandy().setId(newUUID());
        item2.setCount(6);

        dto.setItems(asList(item1, item2));

        return dto;
    }

    @Test
    void addPresentWithValidEntityShouldReturnLocation() throws Exception {
        mockMvc.perform(post("/api/presents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO())))
                .andExpect(status().isNoContent())
                .andExpect(header().string("Location", notNullValue()));
    }

    @Test
    void addPresentWithNotValidEntityShouldReturnErrors() throws Exception {
        mockMvc.perform(post("/api/presents")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors", hasSize(3)));
    }

    @Test
    void removePresentShouldReturn2xx() throws Exception {
        mockMvc.perform(delete("/api/presents/{id}", newUUID()))
                .andExpect(status().isNoContent());
    }

    @Test
    void removePresentWithNonexistentIdShouldReturn404() throws Exception {
        doThrow(new EntityNotFoundException()).when(commandHandler).on(any(RemovePresentCommand.class));

        mockMvc.perform(delete("/api/presents/{id}", newUUID()))
                .andExpect(status().isNotFound());
    }
}