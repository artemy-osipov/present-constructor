package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.testdata.CandyTestData
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import static io.github.artemy.osipov.shop.testdata.CandyTestData.CANDY_ID
import static io.github.artemy.osipov.shop.utils.JsonUtils.toJson
import static io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import static org.hamcrest.Matchers.aMapWithSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class CandyControllerIT extends BaseIT {

    @Autowired
    MockMvc mockMvc

    @Autowired
    CandyRepository candyRepository

    @AfterEach
    void clean() {
        candyRepository.deleteAll()
    }

    @Test
    void 'should add candy with valid data'() {
        def result = mockMvc.perform(post('/api/candies')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(CandyTestData.REST.updateDTO())))

        def addedCandy = candyRepository.findAll()[0]
        result.andExpect(status().isOk())
                .andExpect(content().string('"' + addedCandy.id + '"'))
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
        candyRepository.save(CandyTestData.candy())
        def newName = 'new name'
        def request = CandyTestData.REST.updateDTO().tap {
            name = newName
        }

        mockMvc.perform(put('/api/candies/{id}', CANDY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isNoContent())

        def updatedCandy = candyRepository.findById(CANDY_ID).orElseThrow()
        assert updatedCandy == CandyTestData.candy().tap {
            name = newName
        }
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

        mockMvc.perform(put('/api/candies/{id}', unknownId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(CandyTestData.REST.updateDTO())))
                .andExpect(status().isNotFound())

        assert candyRepository.count() == 0
    }

    @Test
    void 'should remove candy'() {
        candyRepository.save(CandyTestData.candy())

        mockMvc.perform(delete('/api/candies/{id}', CANDY_ID))
                .andExpect(status().isNoContent())

        def hiddenCandy = candyRepository.findById(CANDY_ID).orElseThrow()
        assert !hiddenCandy.active
    }

    @Test
    void 'should not remove unknown candy'() {
        def unknownId = newUUID()

        mockMvc.perform(delete('/api/candies/{id}', unknownId))
                .andExpect(status().isNotFound())

        assert candyRepository.count() == 0
    }
}