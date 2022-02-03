package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.service.present.PresentCommandHandler
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.testdata.PresentTestData
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

import static io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import static io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.mockito.Mockito.doReturn
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(PresentController)
class PresentQueryControllerIT {

    @MockBean
    PresentCommandHandler commandHandler

    @MockBean
    PresentRepository presentRepository

    @Autowired
    MockMvc mockMvc

    @Test
    void 'should get present by id'() {
        def present = PresentTestData.present()
        doReturn(Optional.of(present))
                .when(presentRepository)
                .findById(PRESENT_ID)

        mockMvc.perform(get('/api/presents/{id}', PRESENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                    {
                                      "id": "${present.id}",
                                      "name": "${present.name}",
                                      "price": ${present.price},
                                      "date": "${present.date}",
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
        doReturn(Optional.empty())
                .when(presentRepository)
                .findById(unknownId)

        mockMvc.perform(get('/api/presents/{id}', unknownId))
                .andExpect(status().isNotFound())
    }

    @Test
    void 'should list presents'() {
        doReturn([PresentTestData.present(), PresentTestData.present()])
                .when(presentRepository)
                .findAll()

        mockMvc.perform(get('/api/presents'))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(2)))
    }
}