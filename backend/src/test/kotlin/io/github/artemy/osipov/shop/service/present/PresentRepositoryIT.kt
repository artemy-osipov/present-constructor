package io.github.artemy.osipov.shop.service.present

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.testdata.CandyTestData
import io.github.artemy.osipov.shop.testdata.PresentTestData
import io.github.artemy.osipov.shop.utils.UuidUtils
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class PresentRepositoryIT : BaseIT() {

    @Autowired
    lateinit var repository: PresentRepository

    companion object {
        @BeforeAll
        @JvmStatic
        fun init(@Autowired repository: CandyRepository) {
            repository.add(CandyTestData.candy()).block()
        }

        @AfterAll
        @JvmStatic
        fun clean(@Autowired repository: CandyRepository) {
            repository.deleteAll().block()
        }
    }

    @AfterEach
    fun clean() {
        repository.deleteAll().block()
    }

    @Test
    fun `should find present by id`() {
        val present = PresentTestData.present()
        repository.add(present).block()

        val fromDB = repository.findById(present.id).block()

        assert(
            present == fromDB
        )
    }

    @Test
    fun `should list all presents`() {
        val present = PresentTestData.present()
        repository.add(present).block()

        val fromDB = repository.findAll().collectList().block()

        assert(
            listOf(present) == fromDB
        )
    }

    @Test
    fun `should add and return count`() {
        repository.add(PresentTestData.present()).block()

        val count = repository.count().block()

        assert(
            count == 1L
        )
    }

    @Test
    fun `should not delete by unknown id`() {
        val present = PresentTestData.present()
        repository.add(present).block()

        val deletes = repository.deleteById(UuidUtils.newUUID()).block()
        val count = repository.count().block()

        assert(
            deletes == 0
        )
        assert(
            count == 1L
        )
    }

    @Test
    fun `should delete by id`() {
        val present = PresentTestData.present()
        repository.add(present).block()

        val deletes = repository.deleteById(present.id).block()
        val count = repository.count().block()

        assert(
            deletes == 1
        )
        assert(
            count == 0L
        )
    }

    @Test
    fun `should delete all entities`() {
        repository.add(PresentTestData.present()).block()

        val deletes = repository.deleteAll().block()
        val count = repository.count().block()

        assert(
            deletes == 1
        )
        assert(
            count == 0L
        )
    }
}