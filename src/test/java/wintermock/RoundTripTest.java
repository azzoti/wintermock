package wintermock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.lazyluke.wintermock.jackson.GenericKeyJacksonModule;
import org.lazyluke.wintermock.jackson.SimpleObjectMapperFactory;
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

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = SimpleObjectMapperFactory.getInstance();
        mapper.registerModule(new GenericKeyJacksonModule(Person.class, mapper));
        return mapper;
    }

    @Test
    public void name() throws Exception {

        ObjectMapper mapper = getObjectMapper();
        ComplexType originalObject = TestDataMaker.newComplexType();
        Person person = new InterfaceToMockRealImplementation().pureFunctionReturningComplexType("Boss's \"favourite\" employee", true, originalObject);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(person));

    }




}