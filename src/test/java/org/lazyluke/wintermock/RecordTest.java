package org.lazyluke.wintermock;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lazyluke.wintermock.jackson.GenericKeyJacksonModule;
import org.lazyluke.wintermock.jackson.SimpleObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.lazyluke.wintermock.testclasses.ComplexType;
import org.lazyluke.wintermock.testclasses.Person;

public class RecordTest {

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
    public void testRecordAllInOneGo() throws Exception {
        SimpleObjectMapperFactory.registerModule(new GenericKeyJacksonModule<>(Person.class, SimpleObjectMapperFactory.getInstance()));
        ComplexType complexType = TestDataMaker.newComplexType();
        System.out.println(getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(complexType));
        String returning =  getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(complexType.getBoss());
        String stringParam = "Boss's \"favourite\" employee";
        String functionName = "pureFunctionReturningComplexType";

        Person person = new InterfaceToMockStubRecordingImplementation().pureFunctionReturningComplexType(stringParam, true, complexType);

        FunctionCalls.createCodeAndFilesFromRecordedCalls();

        //try {
        //    Thread.sleep(1000000);
        //} catch (InterruptedException e1) {
        //    e1.printStackTrace();
        //}

    }

}