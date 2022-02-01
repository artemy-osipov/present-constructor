package io.github.artemy.osipov.shop.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Configuration
public class JacksonConfig {

    @JsonComponent
    public static class TrimDeserializer extends StdDeserializer<String> {

        public TrimDeserializer() {
            super(String.class);
        }

        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
            String value = jsonParser.getValueAsString();

            return StringUtils.hasText(value) ? StringUtils.trimWhitespace(value) : null;
        }
    }
}
