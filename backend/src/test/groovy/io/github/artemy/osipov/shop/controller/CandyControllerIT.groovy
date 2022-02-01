package io.github.artemy.osipov.shop.controller


import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import io.github.artemy.osipov.present.exception.EntityNotFoundException

import static org.hamcrest.CoreMatchers.notNullValue
import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.doThrow
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static io.github.artemy.osipov.present.utils.JsonUtils.toJson
import static io.github.artemy.osipov.present.utils.UuidUtils.newUUID

@WebMvcTest(CandyController.class)
class CandyControllerIT {

    @MockBean
    private io.github.artemy.osipov.shop.service.candy.CandyCommandHandler commandHandler

    @Autowired
    private MockMvc mockMvc

    private io.github.artemy.osipov.shop.controller.dto.DEditCandy getUpdateDTO() {
        io.github.artemy.osipov.shop.controller.dto.DEditCandy dto = new io.github.artemy.osipov.shop.controller.dto.DEditCandy()
        dto.setName("name")
        dto.setFirm("firm")
        dto.setPrice(BigDecimal.valueOf(4.2))
        dto.setOrder(51D)

        return dto
    }

    @Test
    void addCandyWithValidEntityShouldReturnLocation() throws Exception {
        mockMvc.perform(post("/api/candies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO())))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", notNullValue()))
    }

    @Test
    void addCandyWithNotValidEntityShouldReturnErrors() throws Exception {
        mockMvc.perform(post("/api/candies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.fieldErrors', hasSize(4)))
    }

    @Test
    void editCandyWithValidEntityShouldReturn2xx() throws Exception {
        mockMvc.perform(put("/api/candies/{id}", newUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO())))
                .andExpect(status().isNoContent())
    }

    @Test
    void editCandyWithNotValidEntityShouldReturnErrors() throws Exception {
        mockMvc.perform(put("/api/candies/{id}", newUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.fieldErrors', hasSize(4)))
    }

    @Test
    void editCandyWithNonExistentIdShouldReturn404() throws Exception {
        doThrow(new EntityNotFoundException()).when(commandHandler).on(any(io.github.artemy.osipov.shop.service.candy.UpdateCandyCommand.class))

        mockMvc.perform(put("/api/candies/{id}", newUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO())))
                .andExpect(status().isNotFound())
    }

    @Test
    void removeCandyWithValidIdShouldReturn2xx() throws Exception {
        mockMvc.perform(delete("/api/candies/{id}", newUUID()))
                .andExpect(status().isNoContent())
    }

    @Test
    void removeCandyWithNonExistentIdShouldReturn404() throws Exception {
        doThrow(new EntityNotFoundException()).when(commandHandler).on(any(io.github.artemy.osipov.shop.service.candy.HideCandyCommand.class))

        mockMvc.perform(delete("/api/candies/{id}", newUUID()))
                .andExpect(status().isNotFound())
    }
}