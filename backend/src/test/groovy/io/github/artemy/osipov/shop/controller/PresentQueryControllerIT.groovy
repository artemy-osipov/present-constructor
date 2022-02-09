package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.testdata.PresentTestData
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc

import java.time.format.DateTimeFormatter

import static io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import static io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class PresentQueryControllerIT extends BaseIT {

    @Autowired
    MockMvc mockMvc

    @BeforeAll
    static void init(@Autowired PresentRepository repository) {
        repository.save(PresentTestData.present())
    }

    @Test
    void 'should get present by id'() {
        def present = PresentTestData.present()

        mockMvc.perform(get('/api/presents/{id}', PRESENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                    {
                                      "id": "${present.id}",
                                      "name": "${present.name}",
                                      "price": ${present.price},
                                      "date": "${present.date.format(DateTimeFormatter.ISO_DATE_TIME)}",
                                      "items": [{
                                        "candyId": "532d5f3b-25e6-4adc-b99a-da74cb5be876",
                                        "count": 5
                                      }]
                                    }"""
                ))
    }

    @Test
    void 'should fail return unknown present'() {
        def unknownId = newUUID()

        mockMvc.perform(get('/api/presents/{id}', unknownId))
                .andExpect(status().isNotFound())
    }

    @Test
    void 'should list presents'() {
        mockMvc.perform(get('/api/presents'))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(1)))
    }
}