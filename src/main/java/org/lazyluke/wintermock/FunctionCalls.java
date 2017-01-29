package org.lazyluke.wintermock;

import static com.github.tomakehurst.wiremock.client.WireMock.getAllServeEvents;
import static org.lazyluke.wintermock.StubbingHelper.convertJsonStringToObjectOfClass;
import static org.lazyluke.wintermock.ToJavaString.replaceDateTimesWithAnyMatch;
import static org.lazyluke.wintermock.ToJavaString.replaceDoubleQuoteWithSingle;
import static org.lazyluke.wintermock.ToJavaString.replaceDoubleQuotedXmlWithAnyMatch;
import static org.lazyluke.wintermock.ToJavaString.replaceIDsWithAnyMatch;
import static org.lazyluke.wintermock.ToJavaString.replaceQuotedAsteriskWithLiteralAsterisk;
import static org.lazyluke.wintermock.ToJavaString.replaceUUIDsWithAnyMatch;
import static wiremock.com.google.common.base.Functions.compose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import wiremock.com.google.common.base.Function;
import wiremock.com.google.common.collect.Lists;

public final class FunctionCalls implements Serializable {

    private LinkedList<FunctionCall> calls = new LinkedList<>();

    public static <T> T checkNextExpectedCallAndReturn(String functionName, FunctionCallParameters actualFunctionParameters, Class<T> classOfReturn) {

        // WireMockServer wireMockServer = new WireMockServer(options().port(8080)); //No-args constructor will start on port 8080, no HTTPS

        HttpEntity<String> request = new HttpEntity<>(actualFunctionParameters.getStringRepresentationOfParameters(), new HttpHeaders());
        String response = new RestTemplate().postForObject(StubbingHelper.WIREMOCK_URL + functionName, request, String.class);
        // TODO log?
        System.out.println(response);

        T ret = (classOfReturn == String.class) ? (T) response : convertJsonStringToObjectOfClass(response, classOfReturn);

        return ret;
    }

    public static  <T> T checkNextExpectedCallAndReturn(String functionName, FunctionCallParameters actualFunctionParameters, TypeReference<T> typeRefOfReturn) {

        HttpEntity<String> request = new HttpEntity<>(actualFunctionParameters.getStringRepresentationOfParameters(), new HttpHeaders());
        String response = new RestTemplate().postForObject(StubbingHelper.WIREMOCK_URL + functionName, request, String.class);
        // TODO log?
        System.out.println(response);

        T ret = StubbingHelper.fromJSON(typeRefOfReturn, response);
        return ret;
    }



    public void addFunctionCall(String functionName, FunctionCallParameters parameters) {
        FunctionCall.record(functionName, parameters);
    }

    public <T> T addFunctionCall(String functionName, FunctionCallParameters parameters, T returnValue) {
        FunctionCall.record(functionName, parameters, returnValue);
        return returnValue;
    }

    public static List<String> containsMatchingCall(String functionName, int callInstance, Map<String, String> parameterExpressionMap, Map<String, String> returnExpressionMap) {
        List<ServeEvent> allServeEvents = getAllServeEvents();
        List<ServeEvent> namedServeEventsMatched = new ArrayList<>(allServeEvents.size());
        List<ServeEvent> namedServeEventsUnmatched = new ArrayList<>(allServeEvents.size());
        List<String> errors = new ArrayList<>();

        for (ServeEvent serveEvent : allServeEvents) {
            String url = serveEvent.getRequest().getUrl();
            // TODO contains?
            if (url.contains(functionName)) {
                if (serveEvent.getWasMatched()) {
                    namedServeEventsMatched.add(serveEvent);
                } else {
                    namedServeEventsUnmatched.add(serveEvent);
                }
            }
        }
        if (namedServeEventsMatched.size() == 0) {
            errors.add("No matched calls to " + functionName + " found. There were " + allServeEvents.size() + " other calls");
            if (namedServeEventsUnmatched.size() > 0) {
                errors.add("There were " + namedServeEventsUnmatched.size() + " calls to " + functionName + " that were not matched ");
            }
            errors.add(getServerEventsAsString(allServeEvents));
            return errors;
        }

        if (callInstance == -1) {
            namedServeEventsMatched = Lists.reverse(namedServeEventsMatched);
            callInstance = 0;
        }

        if (callInstance >= namedServeEventsMatched.size()) {
            errors.add("Trying to verify (zero based) call number " + callInstance + " of " + functionName + " but there are  only " + namedServeEventsMatched.size());
            errors.add(getServerEventsAsString(namedServeEventsMatched));
            return errors;
        }
        ServeEvent serveEvent = namedServeEventsMatched.get(callInstance);
        String params = serveEvent.getRequest().getBodyAsString();
        String returns = serveEvent.getResponseDefinition().getBody();
        if (JsonPathChecker.verifyContains(params, parameterExpressionMap) && JsonPathChecker.verifyContains(returns, returnExpressionMap)) {
            errors.clear();
        } else {
            errors.add(String.format("Found call to %s but not with parameters %s %s, actual parameters were %s and %s", functionName, parameterExpressionMap, returnExpressionMap, params, returns));
        }
        return errors;
    }

    private static String getServerEventsAsString(List<ServeEvent> allServeEvents) {
        try {
            // have to use the wiremock version of ObjectMapper here to respect the wiremock jackson annotations on ServeEvent
            return new wiremock.com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(allServeEvents);
        } catch (wiremock.com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getNumberOfTimesCalled(String functionName) {
        int count = 0;

        for (ServeEvent serveEvent : getAllServeEvents()) {
            String url = serveEvent.getRequest().getUrl();
            if (url.contains(functionName)) {
                count++;
            }
        }
        return count;
    }

    public String toJavaString() {

        return toJavaString(this.calls);
    }

    private static String toJavaString(List<FunctionCall> calls, Function<String, String> stringConverterForExpectedParametersRegexAsJsonString, Function<String, String> stringConverterForReturnValueAsJsonString) {

        return ToJavaString.getJavaString(calls, stringConverterForExpectedParametersRegexAsJsonString, stringConverterForReturnValueAsJsonString);
    }

    private static String toJavaString(List<FunctionCall> calls) {
        return toJavaString(
                calls, compose(replaceQuotedAsteriskWithLiteralAsterisk,
                        compose(replaceIDsWithAnyMatch,
                                compose(replaceDateTimesWithAnyMatch,
                                        compose(replaceUUIDsWithAnyMatch,
                                                compose(replaceDoubleQuoteWithSingle, replaceDoubleQuotedXmlWithAnyMatch))))),
                replaceDoubleQuoteWithSingle);
    }

    public static void createCodeAndFilesFromRecordedCalls() {
        List<StubMapping> mappings = WireMock.listAllStubMappings().getMappings();
        for (StubMapping mapping : mappings) {
            // FunctionCall.create(functionName, "Boss", returning);
            System.out.println("FunctionCall.create(\"" + getFunctionNameFromWiremockUrlPath(mapping.getRequest().getUrlPattern()) + "\", ");
            String functionParametersAsJsonString = mapping.getRequest().getBodyPatterns().get(0).getExpected();
            System.out.println("    Parameter1:\n    " + functionParametersAsJsonString);
            String functionReturnAsJsonString = mapping.getResponse().getBody();
            System.out.println("    Parameter2:\n    " + functionReturnAsJsonString);
            System.out.println(");");
        }
    }




    private static String getFunctionNameFromWiremockUrlPath(String urlPath) {
        return urlPath.replaceAll("/", "");
    }
}
