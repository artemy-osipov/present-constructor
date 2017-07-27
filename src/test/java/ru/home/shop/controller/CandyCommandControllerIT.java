package ru.home.shop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.home.shop.controller.dto.UpdateCandyDTO;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.CandyService;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.home.shop.utils.JsonUtils.toJson;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CandyCommandControllerIT {

    private final CandyService mockService = mock(CandyService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new CandyCommandController(mockService))
            .setControllerAdvice(new ErrorHandler())
            .build();

    private UpdateCandyDTO getCandy() {
        UpdateCandyDTO dto = new UpdateCandyDTO();
        dto.setName("name");
        dto.setFirm("firm");
        dto.setPrice(BigDecimal.valueOf(4.2));
        dto.setOrder(51D);

        return dto;
    }

    @Test
    public void addCandyWithValidEntityShouldReturnId() throws Exception {
        mockMvc.perform(post("/candies")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(getCandy())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void addCandyWithNotValidEntityShouldReturnErrors() throws Exception {
        mockMvc.perform(post("/candies")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors", hasSize(4)));
    }

    @Test
    public void editCandyWithValidEntityShouldReturn200() throws Exception {
        mockMvc.perform(put("/candies/{id}", newUUID())
                .contentType(MediaType.APPLICATION_JSON).content(toJson(getCandy())))
                .andExpect(status().isOk());
    }

    @Test
    public void editCandyWithNotValidEntityShouldReturnErrors() throws Exception {
        mockMvc.perform(put("/candies/{id}", newUUID())
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors", hasSize(4)));
    }

    @Test
    public void editCandyWithNonExistentIdShouldReturn404() throws Exception {
        doThrow(EntityNotFoundException.class).when(mockService).edit(any());

        mockMvc.perform(put("/candies/{id}", newUUID())
                .contentType(MediaType.APPLICATION_JSON).content(toJson(getCandy())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeCandyWithValidIdShouldReturn200() throws Exception {
        mockMvc.perform(delete("/candies/{id}", newUUID()))
                .andExpect(status().isOk());
    }

    @Test
    public void removeCandyWithNonExistentIdShouldReturn404() throws Exception {
        doThrow(EntityNotFoundException.class).when(mockService).remove(any());

        mockMvc.perform(delete("/candies/{id}", newUUID()))
                .andExpect(status().isNotFound());
    }
}