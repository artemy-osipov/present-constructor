package io.github.artemy.osipov.shop.exception

import java.lang.RuntimeException

class EntityNotFoundException(val targetClass: Class<*>, val id: Any) : RuntimeException(
    generateMessage(
        targetClass, id
    )
) {
    private companion object {
        fun generateMessage(targetClass: Class<*>, id: Any): String {
            return String.format(
                "Entity '%s' with id '%s' not exists",
                targetClass.simpleName, id
            )
        }
    }
}