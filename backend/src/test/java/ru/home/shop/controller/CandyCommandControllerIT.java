package ru.home.shop.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.home.shop.api.candy.CreateCandyCommand;
import ru.home.shop.api.candy.RemoveCandyCommand;
import ru.home.shop.controller.dto.CandyDTO;
import ru.home.shop.exception.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.home.shop.utils.JsonUtils.toJson;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@WebMvcTest(CandyCommandController.class)
public class CandyCommandControllerIT {

    @MockBean
    private CommandGateway commandGateway;

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

    @Before
    public void mockInit() {
        when(commandGateway.send(any())).thenReturn(CompletableFuture.completedFuture(null));
    }

    @Test
    public void addCandyWithValidEntityShouldReturnId() throws Exception {
        mockMvc.perform(async(post("/candies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO()))))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", notNullValue()));
    }

    @Test
    public void addCandyWithNotValidEntityShouldReturnErrors() throws Exception {
        mockMvc.perform(post("/candies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors", hasSize(4)));
    }

    @Test
    public void editCandyWithValidEntityShouldReturn200() throws Exception {
        mockMvc.perform(async(put("/candies/{id}", newUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO()))))
                .andExpect(status().isOk());
    }

    @Test
    public void editCandyWithNotValidEntityShouldReturnErrors() throws Exception {
        mockMvc.perform(put("/candies/{id}", newUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors", hasSize(4)));
    }

    @Test
    public void editCandyWithNonExistentIdShouldReturn404() throws Exception {
        when(commandGateway.send(any(CreateCandyCommand.class))).thenReturn(exceptionallyCompletedFuture(new EntityNotFoundException()));

        mockMvc.perform(async(put("/candies/{id}", newUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO()))))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeCandyWithValidIdShouldReturn200() throws Exception {
        mockMvc.perform(async(delete("/candies/{id}", newUUID())))
                .andExpect(status().isOk());
    }

    @Test
    public void removeCandyWithNonExistentIdShouldReturn404() throws Exception {
        when(commandGateway.send(any(RemoveCandyCommand.class))).thenReturn(exceptionallyCompletedFuture(new EntityNotFoundException()));

        mockMvc.perform(async(delete("/candies/{id}", newUUID())))
                .andExpect(status().isNotFound());
    }

    public static <T> CompletableFuture<T> exceptionallyCompletedFuture(Throwable throwable) {
        CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(throwable);
        return future;
    }

    public RequestBuilder async(MockHttpServletRequestBuilder request) throws Exception {
        return asyncDispatch(mockMvc.perform(request)
                .andReturn());
    }
}