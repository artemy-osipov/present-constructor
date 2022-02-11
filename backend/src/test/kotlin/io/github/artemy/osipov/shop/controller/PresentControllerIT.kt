package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.testdata.PresentTestData
import io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import io.github.artemy.osipov.shop.utils.toJson
import org.hamcrest.collection.IsMapWithSize.aMapWithSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.post

class PresentControllerIT : BaseIT() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var presentRepository: PresentRepository

    @AfterEach
    fun clean() {
        presentRepository.deleteAll()
    }

    @Test
    fun `should add present with valid data`() {
        val result = mockMvc.post("/api/presents") {
            contentType = MediaType.APPLICATION_JSON
            content = toJson(PresentTestData.REST.addDTO())
        }

        val addedPresent = presentRepository.findAll()[0]
        result.andExpect {
            status { isOk() }
            content { string("\"${addedPresent.id}\"") }
        }
    }

    @Test
    fun `should not add present with invalid data`() {
        mockMvc.post("/api/presents") {
            contentType = MediaType.APPLICATION_JSON
            content = "{}"
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.details", aMapWithSize<Any, Any>(3))
        }
    }

    @Test
    fun `should remove present`() {
        presentRepository.save(PresentTestData.present())

        mockMvc.delete("/api/presents/{id}", PRESENT_ID)
                .andExpect {
                    status { isNoContent() }
                }
        assert(presentRepository.count() == 0L)
    }

    @Test
    fun `should not remove unknown present`() {
        val unknownId = newUUID()

        mockMvc.delete("/api/presents/{id}", unknownId)
                .andExpect {
                    status { isNoContent() }
                }

        assert(presentRepository.count() == 0L)
    }
}