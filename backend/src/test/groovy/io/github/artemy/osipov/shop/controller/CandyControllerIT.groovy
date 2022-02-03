package io.github.artemy.osipov.shop.controller

import groovy.transform.CompileStatic
import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import io.github.artemy.osipov.shop.service.candy.Candy
import io.github.artemy.osipov.shop.service.candy.CandyCommandHandler
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.service.candy.HideCandyCommand
import io.github.artemy.osipov.shop.service.candy.UpdateCandyCommand
import io.github.artemy.osipov.shop.testdata.CandyTestData
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import static io.github.artemy.osipov.shop.testdata.CandyTestData.CANDY_ID
import static io.github.artemy.osipov.shop.utils.JsonUtils.toJson
import static io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import static org.hamcrest.Matchers.aMapWithSize
import static org.hamcrest.Matchers.notNullValue
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.doThrow
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(CandyController)
@CompileStatic
class CandyControllerIT {

    @MockBean
    CandyCommandHandler commandHandler

    @MockBean
    CandyRepository repository

    @Autowired
    MockMvc mockMvc

    @Test
    void 'should add candy with valid data'() {
        mockMvc.perform(post('/api/candies')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(CandyTestData.REST.updateDTO())))
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()))
    }

    @Test
    void 'should not add candy with invalid data'() {
        mockMvc.perform(post('/api/candies')
                .contentType(MediaType.APPLICATION_JSON)
                .content('{}'))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.details', aMapWithSize(4)))
    }

    @Test
    void 'should edit candy with valid data'() {
        mockMvc.perform(put('/api/candies/{id}', CANDY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(CandyTestData.REST.updateDTO())))
                .andExpect(status().isNoContent())
    }

    @Test
    void 'should not edit candy with invalid data'() {
        mockMvc.perform(put('/api/candies/{id}', CANDY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content('{}'))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.details', aMapWithSize(4)))
    }

    @Test
    void 'should not edit nonexistent candy'() {
        def unknownId = newUUID()
        doThrow(new EntityNotFoundException(Candy, unknownId))
                .when(commandHandler)
                .on(any(UpdateCandyCommand))

        mockMvc.perform(put('/api/candies/{id}', unknownId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(CandyTestData.REST.updateDTO())))
                .andExpect(status().isNotFound())
    }

    @Test
    void 'should remove candy'() {
        mockMvc.perform(delete('/api/candies/{id}', CANDY_ID))
                .andExpect(status().isNoContent())
    }

    @Test
    void 'should not remove unknown candy'() {
        def unknownId = newUUID()
        doThrow(new EntityNotFoundException(Candy, unknownId))
                .when(commandHandler)
                .on(any(HideCandyCommand))

        mockMvc.perform(delete('/api/candies/{id}', unknownId))
                .andExpect(status().isNotFound())
    }
}