package wintermock.extras;

import com.fasterxml.jackson.databind.module.SimpleModule;
import wintermock.testclasses.Person;

public class PersonKeyJacksonModule extends SimpleModule {
    public PersonKeyJacksonModule() {
        addKeyDeserializer(
                Person.class,
                new PersonKeyDeserializer());
        addKeySerializer(
                Person.class,
                new PersonKeySerializer());
    }
}

