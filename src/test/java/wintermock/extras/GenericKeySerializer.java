package wintermock.extras;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * A JsonSerializer
 *
 * @param <T>
 */
public class GenericKeySerializer<T> extends JsonSerializer<T> {

    private Class<T> clazz;
    private ObjectMapper mapper;

    public GenericKeySerializer(Class<T> clazz, ObjectMapper mapper) {
        this.clazz = clazz;
        this.mapper = mapper;
    }

    @Override
    public void serialize(T value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeFieldName(mapper.writeValueAsString(value));
    }
}

