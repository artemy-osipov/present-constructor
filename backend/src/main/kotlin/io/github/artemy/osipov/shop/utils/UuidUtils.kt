package io.github.artemy.osipov.shop.utils

import com.fasterxml.uuid.Generators
import java.util.UUID

object UuidUtils {
    private val generator = Generators.timeBasedGenerator()

    fun newUUID(): UUID {
        return generator.generate()
    }
}
