package io.github.artemy.osipov.shop.utils

import com.github.f4b6a3.ulid.UlidCreator
import java.util.UUID

object UuidUtils {

    fun newUUID(): UUID {
        return UlidCreator.getUlid().toUuid()
    }
}
