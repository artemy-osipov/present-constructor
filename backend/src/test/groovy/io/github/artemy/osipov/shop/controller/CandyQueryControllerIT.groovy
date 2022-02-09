package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.testdata.CandyTestData
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc

import static io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class CandyQueryControllerIT extends BaseIT {

    @Autowired
    MockMvc mockMvc

    @BeforeAll
    static void init(@Autowired CandyRepository repository) {
        repository.save(CandyTestData.candy())
    }

    @Test
    void 'should get candy by id'() {
        def candy = CandyTestData.candy()

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

        mockMvc.perform(get('/api/candies/{id}', unknownId))
                .andExpect(status().isNotFound())
    }

    @Test
    void 'should list candies'() {
        mockMvc.perform(get('/api/candies'))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(1)))
    }
}