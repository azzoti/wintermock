package org.lazyluke.wintermock.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * A Jackson module for serializing and deserializing java Map keys that are not Strings.
 * By default Jackson only serializes Java Map keys using toString. This module serializes the key to Json.
 *
 * @param <T> Java Map key type
 */
public class GenericKeyJacksonModule<T> extends SimpleModule {
    public GenericKeyJacksonModule(Class<T> clazz, ObjectMapper mapper) {
        addKeyDeserializer(clazz, new GenericKeyDeserializer(clazz, mapper));
        addKeySerializer(clazz, new GenericKeySerializer(clazz, mapper));
    }
}

