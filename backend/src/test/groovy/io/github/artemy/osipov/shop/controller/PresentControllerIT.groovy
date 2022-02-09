package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.testdata.PresentTestData
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import static io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import static io.github.artemy.osipov.shop.utils.JsonUtils.toJson
import static io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import static org.hamcrest.Matchers.aMapWithSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class PresentControllerIT extends BaseIT {

    @Autowired
    MockMvc mockMvc

    @Autowired
    PresentRepository presentRepository

    @AfterEach
    void clean() {
        presentRepository.deleteAll()
    }

    @Test
    void 'should add present with valid data'() {
        def result = mockMvc.perform(post('/api/presents')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(PresentTestData.REST.addDTO())))

        def addedPresent = presentRepository.findAll()[0]
        result.andExpect(status().isOk())
                .andExpect(content().string('"' + addedPresent.id + '"'))
    }

    @Test
    void 'should not add present with invalid data'() {
        mockMvc.perform(post('/api/presents')
                .contentType(MediaType.APPLICATION_JSON)
                .content('{}'))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.details', aMapWithSize(3)))
    }

    @Test
    void 'should remove present'() {
        presentRepository.save(PresentTestData.present())

        mockMvc.perform(delete('/api/presents/{id}', PRESENT_ID))
                .andExpect(status().isNoContent())

        assert presentRepository.count() == 0
    }

    @Test
    void 'should not remove unknown present'() {
        def unknownId = newUUID()

        mockMvc.perform(delete('/api/presents/{id}', unknownId))
                .andExpect(status().isNoContent())

        assert presentRepository.count() == 0
    }
}