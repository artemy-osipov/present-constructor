package io.github.artemy.osipov.shop.exception

import java.lang.RuntimeException
import kotlin.reflect.KClass

class EntityNotFoundException(val targetClass: KClass<*>, val id: Any) : RuntimeException(
    generateMessage(
        targetClass, id
    )
) {
    private companion object {
        fun generateMessage(targetClass: KClass<*>, id: Any): String {
            return String.format(
                "Entity '%s' with id '%s' not exists",
                targetClass.simpleName, id
            )
        }
    }
}