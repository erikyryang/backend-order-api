package com.marketplace.backend.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        // Deserializador para String: null -> ""
        module.addDeserializer(String.class, new StdDeserializer<String>(String.class) {
            @Override
            public String deserialize(JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
                String value = p.getValueAsString();
                return value != null ? value : "";
            }
        });

        // Deserializador para Double: null -> 0.0
        module.addDeserializer(Double.class, new StdDeserializer<Double>(Double.class) {
            @Override
            public Double deserialize(JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
                if (p.getCurrentToken() == com.fasterxml.jackson.core.JsonToken.VALUE_NULL) {
                    return 0.0;
                }
                return p.getDoubleValue();
            }
        });

        // Deserializador para Integer: null -> 0
        module.addDeserializer(Integer.class, new StdDeserializer<Integer>(Integer.class) {
            @Override
            public Integer deserialize(JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
                if (p.getCurrentToken() == com.fasterxml.jackson.core.JsonToken.VALUE_NULL) {
                    return 0;
                }
                return p.getIntValue();
            }
        });

        // Deserializador para UUID: null -> UUID.randomUUID()
        module.addDeserializer(UUID.class, new StdDeserializer<UUID>(UUID.class) {
            @Override
            public UUID deserialize(JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
                String value = p.getValueAsString();
                return value != null && !value.isEmpty() ? UUID.fromString(value) : UUID.randomUUID();
            }
        });

        objectMapper.registerModule(module);
        return objectMapper;
    }
}
