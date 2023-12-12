package io.github.artemy.osipov.shop.service.present

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.testdata.CandyTD
import io.github.artemy.osipov.shop.testdata.PresentTD
import io.github.artemy.osipov.shop.utils.UuidUtils
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
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
        fun init(@Autowired repository: CandyRepository) = runTest {
            repository.add(CandyTD.candy())
        }

        @AfterAll
        @JvmStatic
        fun clean(@Autowired repository: CandyRepository) = runTest {
            repository.deleteAll()
        }
    }

    @AfterEach
    fun clean() = runTest {
        repository.deleteAll()
    }

    @Test
    fun `should find present by id`() = runTest {
        val present = PresentTD.present()
        repository.add(present)

        val fromDB = repository.findById(present.id)

        assert(
            present == fromDB
        )
    }

    @Test
    fun `should list all presents`() = runTest {
        val present = PresentTD.present()
        repository.add(present)

        val fromDB = repository.findAll().toList()

        assert(
            listOf(present) == fromDB
        )
    }

    @Test
    fun `should add and return count`() = runTest {
        repository.add(PresentTD.present())

        val count = repository.count()

        assert(
            count == 1L
        )
    }

    @Test
    fun `should not delete by unknown id`() = runTest {
        val present = PresentTD.present()
        repository.add(present)

        repository.deleteById(UuidUtils.newUUID())
        val count = repository.count()

        assert(
            count == 1L
        )
    }

    @Test
    fun `should delete by id`() = runTest {
        val present = PresentTD.present()
        repository.add(present)

        repository.deleteById(present.id)
        val count = repository.count()

        assert(
            count == 0L
        )
    }

    @Test
    fun `should delete all entities`() = runTest {
        repository.add(PresentTD.present())

        repository.deleteAll()
        val count = repository.count()

        assert(
            count == 0L
        )
    }
}