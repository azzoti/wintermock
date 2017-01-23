package wintermock.extras;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import wintermock.testclasses.Person;

import java.io.IOException;

public class PersonKeyDeserializer extends KeyDeserializer {
    @Override
    public Person deserializeKey(final String key,
                                 final DeserializationContext ctxt)
            throws IOException {
        //System.out.println(key);
        return new ObjectMapper().readValue(key, Person.class);
    }
}

