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
import ru.home.shop.api.present.RemovePresentCommand;
import ru.home.shop.controller.dto.AddPresentDTO;
import ru.home.shop.controller.dto.EntityDTO;
import ru.home.shop.controller.dto.PresentItemDTO;
import ru.home.shop.exception.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.home.shop.utils.JsonUtils.toJson;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@WebMvcTest(PresentCommandController.class)
public class PresentCommandControllerIT {

    @MockBean
    private CommandGateway commandGateway;

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

    @Before
    public void mockInit() {
        when(commandGateway.send(any())).thenReturn(CompletableFuture.completedFuture(null));
    }

    @Test
    public void addPresentWithValidEntityShouldReturnLocation() throws Exception {
        mockMvc.perform(async(post("/presents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO()))))
                .andExpect(status().isNoContent())
                .andExpect(header().string("Location", notNullValue()));
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
    public void removePresentShouldReturn2xx() throws Exception {
        mockMvc.perform(async(delete("/presents/{id}", newUUID())))
                .andExpect(status().isNoContent());
    }

    @Test
    public void removePresentWithNonexistentIdShouldReturn404() throws Exception {
        when(commandGateway.send(any(RemovePresentCommand.class))).thenReturn(exceptionallyCompletedFuture(new EntityNotFoundException()));

        mockMvc.perform(async(delete("/presents/{id}", newUUID())))
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