package wintermock.extras;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * A Jackson module for serializing and deserializing java Map keys that are not Strings
 *
 * @param <T> Java Map key type
 */
public class GenericKeyJacksonModule<T> extends SimpleModule {
    public GenericKeyJacksonModule(Class<T> clazz, ObjectMapper mapper) {
        addKeyDeserializer(clazz, new GenericKeyDeserializer(clazz, mapper));
        addKeySerializer(clazz, new GenericKeySerializer(clazz, mapper));
    }
}

