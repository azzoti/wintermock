package org.lazyluke.wintermock.jackson;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GenericKeyDeserializer<T> extends KeyDeserializer {

    private Class<T> clazz;
    private ObjectMapper mapper;

    public GenericKeyDeserializer(final Class<T> clazz, final ObjectMapper mapper) {
        this.clazz = clazz;
        this.mapper = mapper;
    }

    @Override
    public T deserializeKey(final String key, final DeserializationContext ctxt) throws IOException {
        return mapper.readValue(key, clazz);
    }
}

