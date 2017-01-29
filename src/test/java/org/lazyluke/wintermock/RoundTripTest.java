package org.lazyluke.wintermock;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.lazyluke.wintermock.jackson.GenericKeyJacksonModule;
import org.lazyluke.wintermock.jackson.SimpleObjectMapperFactory;
import org.lazyluke.wintermock.testclasses.ComplexType;
import org.lazyluke.wintermock.testclasses.Person;

public class RoundTripTest {
    @Test
    public void Test_GivenAComplexObject_WhenTheObjectIsRoundTrippedToAndFromJson_ThenAnEqualComplexObjectIsReturned() throws Exception {
        ComplexType originalObject = TestDataMaker.newComplexType();


        ObjectMapper mapper = getObjectMapper();

        String originalObjectAsJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(originalObject);
        System.out.println(originalObjectAsJsonString);

        ComplexType rebuiltOriginalObjectFromJsonString = mapper.readValue(originalObjectAsJsonString, ComplexType.class);

        String rebuiltOriginalObjectAsJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rebuiltOriginalObjectFromJsonString);

        assertEquals(originalObjectAsJsonString, rebuiltOriginalObjectAsJsonString);


    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = SimpleObjectMapperFactory.getInstance();
        mapper.registerModule(new GenericKeyJacksonModule(Person.class, mapper));
        return mapper;
    }

    @Test
    public void Test_GivenARealImplementation_WhenTheRealImplementationIsCalled_ThenItDoesSomethingSensible() throws Exception {

        ObjectMapper mapper = getObjectMapper();
        ComplexType originalObject = TestDataMaker.newComplexType();
        Person person = new InterfaceToMockRealImplementation().pureFunctionReturningComplexType("Boss's \"favourite\" employee", true, originalObject);
        String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(person);
        System.out.println(actual);
        assertEquals("{\n" +
                "  \"name\" : \"Joan Collins\",\n" +
                "  \"age\" : 91,\n" +
                "  \"eyeColor\" : \"RED\",\n" +
                "  \"nationality\" : \"FRANCE\"\n" +
                "}", actual);

    }




}