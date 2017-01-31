package org.lazyluke.wintermock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.getAllServeEvents;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.lazyluke.wintermock.StubbingHelper.getAsJsonString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import wiremock.com.google.common.collect.Lists;

public final class FunctionCall implements Serializable {

    public static final String RECORDED = "/recorded/";

    public static void record(String nameOfTheFunctionCalled, FunctionCallParameters regExToMatchFunctionParametersSerializedAsJson, Object functionReturnAsJsonString) {

        String jsonReturn = functionReturnAsJsonString instanceof String ? (String) functionReturnAsJsonString : StubbingHelper.getAsJsonString(functionReturnAsJsonString);

        // TODO parameters.getStringRepresentationOfParameters() is BEFORE delegate call
        // TODO parameters.getParameters() has parameters after call - may not have the same StringRepresentationOfParameters AFTER call for IMPURE FUNCTION
        stubFor(post(urlMatching(RECORDED + nameOfTheFunctionCalled))
                .withRequestBody(equalTo(regExToMatchFunctionParametersSerializedAsJson.getStringRepresentationOfParameters()))
                .willReturn(aResponse().withBody(jsonReturn).withStatus(200)));

    }

    public static void record(String functionName, FunctionCallParameters parameters) {

        // TODO parameters.getStringRepresentationOfParameters() is BEFORE delegate call
        // TODO parameters.getParameters() has parameters after call - may not have the same StringRepresentationOfParameters AFTER call for IMPURE FUNCTION
        stubFor(post(urlMatching(RECORDED + functionName))
                .withRequestBody(equalToJson(parameters.getStringRepresentationOfParameters()))
                .willReturn(aResponse().withStatus(204)));

    }


    // TODO initally rename "expect"
    // TODO then provide mockito style alternative when X.y then return X
    public static void create(String nameOfTheFunctionCalled, String regExToMatchFunctionParametersSerializedAsJson, String functionReturnAsJsonString) {

        regExToMatchFunctionParametersSerializedAsJson = replaceSingleQuoteWithDouble(regExToMatchFunctionParametersSerializedAsJson);
        functionReturnAsJsonString = replaceSingleQuoteWithDouble(functionReturnAsJsonString);

        String escapeJsonForRegex = escapeJsonForRegex(regExToMatchFunctionParametersSerializedAsJson);
        if (!escapeJsonForRegex.startsWith(".*")) {
            escapeJsonForRegex = ".*" + escapeJsonForRegex;
        }
        if (!escapeJsonForRegex.endsWith(".*")) {
            escapeJsonForRegex = escapeJsonForRegex + ".*";
        }
        stubFor(post(urlMatching("/" + nameOfTheFunctionCalled)).withRequestBody(matching(escapeJsonForRegex)).willReturn(
                aResponse().withBody(functionReturnAsJsonString).withStatus(201)));

    }






    public static String replaceSingleQuoteWithDouble(String s) {
        return (s == null) ? "" : s.replace('\'', '"');
    }

    public static String escapeJsonForRegex(String expected) {
        return expected
                .replaceAll("\\{", "\\\\{")
                .replaceAll("}", "\\\\}")
                .replaceAll("\\[", "\\\\[")
                .replaceAll("]", "\\\\]")
                ;
    }

    public static String createCodeAndFilesFromRecordedCalls() {
        List<StubMapping> mappings = WireMock.listAllStubMappings().getMappings();
        StringBuilder sb = new StringBuilder();
        for (StubMapping mapping : mappings) {
            sb.append("FunctionCall.create(\"" + getFunctionNameFromWiremockUrlPath(mapping.getRequest().getUrlPattern()) + "\", ");
            String functionParametersAsJsonString = mapping.getRequest().getBodyPatterns().get(0).getExpected();
            sb.append("    Parameter1:\n    " + functionParametersAsJsonString);
            String functionReturnAsJsonString = mapping.getResponse().getBody();
            sb.append("    Parameter2:\n    " + functionReturnAsJsonString);
            sb.append(");");
        }
        return sb.toString();
    }

    private static String getFunctionNameFromWiremockUrlPath(String urlPath) {
        return urlPath.replaceAll("/recorded/", "");
    }

    public static <T> T checkNextExpectedCallAndReturn(String functionName, FunctionCallParameters actualFunctionParameters, Class<T> classOfReturn) {

        // WireMockServer wireMockServer = new WireMockServer(options().port(8080)); //No-args constructor will start on port 8080, no HTTPS

        HttpEntity<String> request = new HttpEntity<>(actualFunctionParameters.getStringRepresentationOfParameters(), new HttpHeaders());
        String response = new RestTemplate().postForObject(StubbingHelper.WIREMOCK_URL + functionName, request, String.class);
        // TODO log?
        System.out.println(response);

        T ret = (classOfReturn == String.class) ? (T) response : StubbingHelper.convertJsonStringToObjectOfClass(response, classOfReturn);

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

}
