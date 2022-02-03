package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.service.candy.CandyCommandHandler
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.testdata.CandyTestData
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

import static io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.mockito.Mockito.any
import static org.mockito.Mockito.doReturn
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CandyController)
class CandyQueryControllerIT {

    @MockBean
    CandyCommandHandler commandHandler

    @MockBean
    CandyRepository repository

    @Autowired
    MockMvc mockMvc

    @Test
    void 'should get candy by id'() {
        def candy = CandyTestData.candy()
        doReturn(Optional.of(candy))
                .when(repository)
                .findById(any())

        mockMvc.perform(get('/api/candies/{id}', candy.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                    {
                                      "id": "${candy.id}",
                                      "name": "${candy.name}",
                                      "firm": "${candy.firm}",
                                      "price": ${candy.price},
                                      "order": ${candy.order}
                                    }"""
                ))
    }

    @Test
    void 'should fail return unknown candy'() {
        def unknownId = newUUID()
        doReturn(Optional.empty())
                .when(repository)
                .findById(any(UUID))

        mockMvc.perform(get('/api/candies/{id}', unknownId))
                .andExpect(status().isNotFound())
    }

    @Test
    void 'should list candies'() {
        doReturn([CandyTestData.candy(), CandyTestData.candy()])
                .when(repository)
                .findAll()

        mockMvc.perform(get('/api/candies'))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(2)))
    }
}