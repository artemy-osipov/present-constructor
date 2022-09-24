package io.github.artemy.osipov.shop

import io.github.artemy.osipov.shop.utils.UuidUtils
import org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator
import reactor.blockhound.BlockHound
import reactor.blockhound.integration.BlockHoundIntegration

class CustomBlockHoundIntegration : BlockHoundIntegration {

    override fun applyTo(builder: BlockHound.Builder) {
        builder
            .allowBlockingCallsInside(UuidUtils::class.qualifiedName, "newUUID")
            .allowBlockingCallsInside(LocaleContextMessageInterpolator::class.qualifiedName, "interpolate")
    }
}