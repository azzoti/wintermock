package org.lazyluke.wintermock;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lazyluke.wintermock.jackson.GenericKeyJacksonModule;
import org.lazyluke.wintermock.jackson.SimpleObjectMapperFactory;
import org.lazyluke.wintermock.testclasses.ComplexType;
import org.lazyluke.wintermock.testclasses.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;

public class PlaybackTest {

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = SimpleObjectMapperFactory.getInstance();
        mapper.registerModule(new GenericKeyJacksonModule(Person.class, mapper));
        return mapper;
    }

    private static WireMockServer wireMockServer;

    @BeforeClass
    public static void start() throws InterruptedException {
        wireMockServer = new WireMockServer(wireMockConfig());
        wireMockServer.start();
        Thread.sleep(1000);
    }

    @AfterClass
    public static void stop() throws InterruptedException {
        wireMockServer.shutdown();
        Thread.sleep(1000);
    }

    @Before
    public void before() throws InterruptedException {
        wireMockServer.resetAll();
        Thread.sleep(1000);
    }

    @Test
    public void testPlayBackAllInOneGo() throws Exception {
        SimpleObjectMapperFactory.registerModule(new GenericKeyJacksonModule<>(Person.class, SimpleObjectMapperFactory.getInstance()));
        ComplexType complexType = TestDataMaker.newComplexType();
        System.out.println(getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(complexType));
        String returning =  getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(complexType.getBoss());
        String stringParam = "Boss's \"favourite\" employee";
        String functionName = "pureFunctionReturningComplexType";

        FunctionCall.create(functionName, "Boss", returning);

        Person person = null;
        try {
            person = new InterfaceToMockStubPlaybackImplementation().pureFunctionReturningComplexType(stringParam, true, complexType);
        } catch (Exception e) {
            e.printStackTrace();

            // List<NearMiss> nearMisses = WireMock.findNearMissesFor(myLoggedRequest);
        }
        System.out.println(person);

        List<List<String>> table =
                asList(
                        asList("PARAMETER", "c1", "basicFields.primitiveIntField", "2147483647"),
                        asList("PARAMETER", "c1", "basicFields.integerField", "-2147483648"),
                        asList("PARAMETER", "c1", "basicFields.primitiveBooleanField", "false"),
                        asList("PARAMETER", "c1", "employees[0].name", "Jane Smith"),
                        asList("PARAMETER", "c1", "employees[1].eyeColor", "BLACK"),
                        asList("RETURNING", "return", "name", "Daisy Macarthur"),
                        asList("RETURNING", "return", "age", "54")
                );

        Map<String, String> parameterExpressionMap = CucumberSupport.getExpressionMapFromTable(table, CucumberSupport.PARAMETER, ".*", true);
        Map<String, String> returnExpressionMap = CucumberSupport.getExpressionMapFromTable(table, CucumberSupport.RETURNING, ".*", true);

        List<String> errors = FunctionCalls.containsMatchingCall(functionName, -1, parameterExpressionMap, returnExpressionMap);
        for (String error : errors) {
            System.out.println(error);
        }

        Assert.assertEquals(0, errors.size());
        Assert.assertEquals(1, FunctionCalls.getNumberOfTimesCalled(functionName));

        //try {
        //    Thread.sleep(1000000);
        //} catch (InterruptedException e1) {
        //    e1.printStackTrace();
        //}

    }
}