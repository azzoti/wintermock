package org.lazyluke.wintermock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.lazyluke.wintermock.StubbingHelper.getAsJsonString;

import java.io.Serializable;

public final class FunctionCall implements Serializable {


    public static void record(String nameOfTheFunctionCalled, FunctionCallParameters regExToMatchFunctionParametersSerializedAsJson, Object functionReturnAsJsonString) {

        String jsonReturn = functionReturnAsJsonString instanceof String ? (String) functionReturnAsJsonString : getAsJsonString(functionReturnAsJsonString);

        // TODO parameters.getStringRepresentationOfParameters() is BEFORE delegate call
        // TODO parameters.getParameters() has parameters after call - may not have the same StringRepresentationOfParameters AFTER call for IMPURE FUNCTION
        stubFor(post(urlMatching("/" + nameOfTheFunctionCalled))
                .withRequestBody(equalTo(regExToMatchFunctionParametersSerializedAsJson.getStringRepresentationOfParameters()))
                .willReturn(aResponse().withBody(jsonReturn).withStatus(200)));

    }

    public static void record(String functionName, FunctionCallParameters parameters) {

        // TODO parameters.getStringRepresentationOfParameters() is BEFORE delegate call
        // TODO parameters.getParameters() has parameters after call - may not have the same StringRepresentationOfParameters AFTER call for IMPURE FUNCTION
        stubFor(post(urlMatching("/" + functionName))
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

}
