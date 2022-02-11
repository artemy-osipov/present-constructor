package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.testdata.PresentTestData
import io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.format.DateTimeFormatter

class PresentQueryControllerIT : BaseIT() {

    @Autowired
    lateinit var mockMvc: MockMvc

    companion object {
        @BeforeAll
        @JvmStatic
        fun init(@Autowired repository: PresentRepository) {
            repository.save(PresentTestData.present())
        }
    }


    @Test
    fun `should get present by id`() {
        val present = PresentTestData.present()

        mockMvc.get("/api/presents/{id}", PRESENT_ID)
                .andExpect {
                    status { isOk() }
                    content {
                        json("""
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
                        )
                    }
                }
    }

    @Test
    fun `should fail return unknown present`() {
        val unknownId = newUUID()

        mockMvc.get("/api/presents/{id}", unknownId)
                .andExpect {
                    status { isNotFound() }
                }
    }

    @Test
    fun `should list presents`() {
        mockMvc.get("/api/presents")
                .andExpect {
                    status { isOk() }
                    jsonPath("$", hasSize<Any>(1))
                }
    }
}