package com.marketplace.backend.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.UUID;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        // Desserializador para String: retorna "" se o valor for null
        module.addDeserializer(String.class, new StdDeserializer<>(String.class) {
            @Override
            public String deserialize(JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
                String value = p.getValueAsString();
                return value != null ? value : "";
            }
        });

        // Desserializador para Double: retorna 0.0 se o valor for null
        module.addDeserializer(Double.class, new StdDeserializer<Double>(Double.class) {
            @Override
            public Double deserialize(JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
                if (p.getCurrentToken() == com.fasterxml.jackson.core.JsonToken.VALUE_NULL) {
                    return 0.0;
                }
                return p.getDoubleValue();
            }
        });

        // Desserializador para Integer: retorna 0 se o valor for null
        module.addDeserializer(Integer.class, new StdDeserializer<>(Integer.class) {
            @Override
            public Integer deserialize(JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
                if (p.getCurrentToken() == com.fasterxml.jackson.core.JsonToken.VALUE_NULL) {
                    return 0;
                }
                return p.getIntValue();
            }
        });

        // Desserializador ajustado para UUID
        module.addDeserializer(UUID.class, new StdDeserializer<>(UUID.class) {
            @Override
            public UUID deserialize(JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
                String value = p.getValueAsString();
                if (value == null || value.isEmpty() || value.equalsIgnoreCase("string")) {
                    return null; // Permite que o banco de dados gere o UUID
                }
                try {
                    return UUID.fromString(value);
                } catch (IllegalArgumentException e) {
                    return null; // Ou lançar uma exceção personalizada, se preferir
                }
            }
        });

        objectMapper.registerModule(module);
        // Configurações adicionais recomendadas
        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
        return objectMapper;
    }
}