package wintermock.extras;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import wintermock.testclasses.Person;

import java.io.IOException;

public class PersonKeySerializer extends JsonSerializer<Person> {

    @Override
    public void serialize(Person value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeFieldName(new ObjectMapper().writeValueAsString(value));
    }
}

