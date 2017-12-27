package ru.home.shop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.home.shop.api.candy.RemoveCandyCommand;
import ru.home.shop.api.candy.UpdateCandyCommand;
import ru.home.shop.controller.dto.CandyDTO;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.command.candy.CandyCommandHandler;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.home.shop.utils.JsonUtils.toJson;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@WebMvcTest(CandyCommandController.class)
@WithMockUser
public class CandyCommandControllerIT {

    @MockBean
    private CandyCommandHandler commandHandler;

    @Autowired
    private MockMvc mockMvc;

    private CandyDTO getUpdateDTO() {
        CandyDTO dto = new CandyDTO();
        dto.setName("name");
        dto.setFirm("firm");
        dto.setPrice(BigDecimal.valueOf(4.2));
        dto.setOrder(51D);

        return dto;
    }

    @Test
    @WithAnonymousUser
    public void addCandyWithAnonymousUserShouldReturn401() throws Exception {
        mockMvc.perform(post("/api/candies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addCandyWithValidEntityShouldReturnLocation() throws Exception {
        mockMvc.perform(post("/api/candies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO())))
                .andExpect(status().isNoContent())
                .andExpect(header().string("Location", notNullValue()));
    }

    @Test
    public void addCandyWithNotValidEntityShouldReturnErrors() throws Exception {
        mockMvc.perform(post("/api/candies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors", hasSize(4)));
    }

    @Test
    public void editCandyWithValidEntityShouldReturn2xx() throws Exception {
        mockMvc.perform(put("/api/candies/{id}", newUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO())))
                .andExpect(status().isNoContent());
    }

    @Test
    public void editCandyWithNotValidEntityShouldReturnErrors() throws Exception {
        mockMvc.perform(put("/api/candies/{id}", newUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors", hasSize(4)));
    }

    @Test
    public void editCandyWithNonExistentIdShouldReturn404() throws Exception {
        doThrow(new EntityNotFoundException()).when(commandHandler).on(any(UpdateCandyCommand.class));

        mockMvc.perform(put("/api/candies/{id}", newUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeCandyWithValidIdShouldReturn2xx() throws Exception {
        mockMvc.perform(delete("/api/candies/{id}", newUUID()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeCandyWithNonExistentIdShouldReturn404() throws Exception {
        doThrow(new EntityNotFoundException()).when(commandHandler).on(any(RemoveCandyCommand.class));

        mockMvc.perform(delete("/api/candies/{id}", newUUID()))
                .andExpect(status().isNotFound());
    }
}