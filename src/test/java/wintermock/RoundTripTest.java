package wintermock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.junit.Assert;
import org.junit.Test;
import wintermock.extras.GenericKeyJacksonModule;
import wintermock.testclasses.ComplexType;
import wintermock.testclasses.Person;

public class RoundTripTest {
    @Test
    public void Test_GivenAComplexObject_WhenTheObjectIsRoundTrippedToAndFromJson_ThenAnEqualComplexObjectIsReturned() throws Exception {
        ComplexType originalObject = TestDataMaker.newComplexType();


        ObjectMapper mapper = getObjectMapper();

        String originalObjectAsJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(originalObject);
        System.out.println(originalObjectAsJsonString);

        ComplexType rebuiltOriginalObjectFromJsonString = mapper.readValue(originalObjectAsJsonString, ComplexType.class);

        String rebuiltOriginalObjectAsJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rebuiltOriginalObjectFromJsonString);

        Assert.assertEquals(originalObjectAsJsonString, rebuiltOriginalObjectAsJsonString);


    }

    @Test
    public void name() throws Exception {

        ObjectMapper mapper = getObjectMapper();
        ComplexType originalObject = TestDataMaker.newComplexType();
        Person person = new InterfaceToMockRealImplementation().pureFunctionReturningComplexType("Boss's \"favourite\" employee", true, originalObject);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(person));

    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JodaModule());
        mapper.registerModule(new GenericKeyJacksonModule(Person.class, mapper));
        return mapper;
    }


}