package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.testdata.CandyTestData
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

class CandyQueryControllerIT : BaseIT() {

    @Autowired
    lateinit var mockMvc: MockMvc

    companion object {
        @BeforeAll
        @JvmStatic
        fun init(@Autowired repository: CandyRepository) {
            repository.save(CandyTestData.candy())
        }
    }


    @Test
    fun `should get candy by id`() {
        val candy = CandyTestData.candy()

        mockMvc.get("/api/candies/{id}", candy.id)
                .andExpect {
                    status { isOk() }
                    content {
                        json("""
                                    {
                                      "id": "${candy.id}",
                                      "name": "${candy.name}",
                                      "firm": "${candy.firm}",
                                      "price": ${candy.price},
                                      "order": ${candy.order}
                                    }""")
                    }
                }
    }

    @Test
    fun `should fail return unknown candy`() {
        val unknownId = newUUID()

        mockMvc.get("/api/candies/{id}", unknownId)
                .andExpect {
                    status { isNotFound() }
                }
    }

    @Test
    fun `should list all candies`() {
        mockMvc.get("/api/candies")
                .andExpect {
                    status { isOk() }
                    jsonPath("$", hasSize<String>(1))
                }
    }
}