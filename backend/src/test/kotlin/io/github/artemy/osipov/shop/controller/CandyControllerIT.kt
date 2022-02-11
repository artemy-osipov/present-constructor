package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.testdata.CandyTestData
import io.github.artemy.osipov.shop.testdata.CandyTestData.CANDY_ID
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import io.github.artemy.osipov.shop.utils.toJson
import org.hamcrest.Matchers.aMapWithSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

class CandyControllerIT : BaseIT() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var candyRepository: CandyRepository

    @AfterEach
    fun clean() {
        candyRepository.deleteAll()
    }

    @Test
    fun `should add candy with valid data`() {
        val result = mockMvc.post("/api/candies") {
            contentType = MediaType.APPLICATION_JSON
            content = toJson(CandyTestData.REST.updateDTO())
        }

        val addedCandy = candyRepository.findAll()[0]
        result.andExpect {
            status { isOk() }
            content { string("\"${addedCandy.id}\"") }
        }
    }

    @Test
    fun `should not add candy with invalid data`() {
        mockMvc.post("/api/candies") {
            contentType = MediaType.APPLICATION_JSON
            content = "{}"
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.details", aMapWithSize<Any, Any>(4))
        }
    }

    @Test
    fun `should edit candy with valid data`() {
        candyRepository.save(CandyTestData.candy())
        val newName = "new name"
        val request = CandyTestData.REST.updateDTO().apply {
            name = newName
        }

        mockMvc.put("/api/candies/{id}", CANDY_ID) {
            contentType = MediaType.APPLICATION_JSON
            content = toJson(request)
        }.andExpect {
            status { isNoContent() }
        }

        val updatedCandy = candyRepository.findById(CANDY_ID).orElseThrow()
        assert(
                updatedCandy == CandyTestData.candy().apply {
                    name = newName
                }
        )
    }

    @Test
    fun `should not edit candy with invalid data`() {
        mockMvc.put("/api/candies/{id}", CANDY_ID) {
            contentType = MediaType.APPLICATION_JSON
            content = "{}"
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.details", aMapWithSize<Any, Any>(4))
        }
    }

    @Test
    fun `should not edit nonexistent candy`() {
        val unknownId = newUUID()

        mockMvc.put("/api/candies/{id}", unknownId) {
            contentType = MediaType.APPLICATION_JSON
            content = toJson(CandyTestData.REST.updateDTO())
        }.andExpect {
            status { isNotFound() }
        }

        assert(candyRepository.count() == 0L)
    }

    @Test
    fun `should remove candy`() {
        candyRepository.save(CandyTestData.candy())

        mockMvc.delete("/api/candies/{id}", CANDY_ID)
                .andExpect {
                    status { isNoContent() }
                }

        val hiddenCandy = candyRepository.findById(CANDY_ID).orElseThrow()
        assert(!hiddenCandy.active)
    }

    @Test
    fun `should not remove unknown candy`() {
        val unknownId = newUUID()

        mockMvc.delete("/api/candies/{id}", unknownId)
                .andExpect {
                    status { isNotFound() }
                }

        assert(candyRepository.count() == 0L)
    }
}