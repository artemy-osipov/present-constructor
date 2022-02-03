package io.github.artemy.osipov.shop.controller

import groovy.transform.CompileStatic
import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import io.github.artemy.osipov.shop.service.present.Present
import io.github.artemy.osipov.shop.service.present.PresentCommandHandler
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.service.present.RemovePresentCommand
import io.github.artemy.osipov.shop.testdata.PresentTestData
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import static io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import static io.github.artemy.osipov.shop.utils.JsonUtils.toJson
import static io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import static org.hamcrest.Matchers.aMapWithSize
import static org.hamcrest.Matchers.notNullValue
import static org.mockito.Mockito.any
import static org.mockito.Mockito.doThrow
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@CompileStatic
@WebMvcTest(PresentController.class)
class PresentControllerIT {

    @MockBean
    PresentCommandHandler commandHandler

    @MockBean
    PresentRepository presentRepository

    @Autowired
    MockMvc mockMvc

    @Test
    void 'should add present with valid data'() {
        mockMvc.perform(post('/api/presents')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(PresentTestData.REST.addDTO())))
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()))
    }

    @Test
    void 'should not add present with invalid data'() throws Exception {
        mockMvc.perform(post('/api/presents')
                .contentType(MediaType.APPLICATION_JSON)
                .content('{}'))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.details', aMapWithSize(3)))
    }

    @Test
    void 'should remove present'() throws Exception {
        mockMvc.perform(delete('/api/presents/{id}', PRESENT_ID))
                .andExpect(status().isNoContent())
    }

    @Test
    void 'should not remove unknown present'() throws Exception {
        def unknownId = newUUID()
        doThrow(new EntityNotFoundException(Present, unknownId))
                .when(commandHandler)
                .on(any(RemovePresentCommand))

        mockMvc.perform(delete('/api/presents/{id}', unknownId))
                .andExpect(status().isNotFound())
    }
}