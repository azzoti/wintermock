package wintermock;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.lazyluke.wintermock.CucumberSupport;
import org.lazyluke.wintermock.FunctionCall;
import org.lazyluke.wintermock.FunctionCalls;
import org.lazyluke.wintermock.jackson.GenericKeyJacksonModule;
import org.lazyluke.wintermock.jackson.SimpleObjectMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import wintermock.testclasses.ComplexType;
import wintermock.testclasses.Person;

public class RecordTest {

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = SimpleObjectMapperFactory.getInstance();
        mapper.registerModule(new GenericKeyJacksonModule(Person.class, mapper));
        return mapper;
    }

    private WireMockServer wireMockServer;


    public void start() {
        wireMockServer = new WireMockServer(wireMockConfig());
        wireMockServer.start();
    }

    @Test
    public void testRecordAllInOneGo() throws Exception {
        start();
        SimpleObjectMapperFactory.registerModule(new GenericKeyJacksonModule<>(Person.class, SimpleObjectMapperFactory.getInstance()));
        ComplexType complexType = TestDataMaker.newComplexType();
        System.out.println(getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(complexType));
        String returning =  getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(complexType.getBoss());
        String stringParam = "Boss's \"favourite\" employee";
        String functionName = "pureFunctionReturningComplexType";

        // FunctionCall.create(functionName, "Boss", returning);

        Person person = new InterfaceToMockStubRecordingImplementation().pureFunctionReturningComplexType(stringParam, true, complexType);

        List<StubMapping> mappings = WireMock.listAllStubMappings().getMappings();
        for (StubMapping mapping : mappings) {
            System.out.println(mapping.getRequest().getBodyPatterns().get(0).getExpected());
            System.out.println(mapping.getResponse().getBody());

        }

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

    }
}