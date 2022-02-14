package io.github.artemy.osipov.shop.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import org.springframework.boot.jackson.JsonComponent
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils

@Configuration
class JacksonConfig {

    @JsonComponent
    class TrimDeserializer : StdDeserializer<String?>(
        String::class.java
    ) {
        override fun deserialize(jsonParser: JsonParser, context: DeserializationContext): String? {
            val value = jsonParser.valueAsString
            return if (StringUtils.hasText(value)) StringUtils.trimWhitespace(value) else null
        }
    }
}