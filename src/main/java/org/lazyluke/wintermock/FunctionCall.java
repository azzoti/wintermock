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
import wiremock.com.google.common.base.Function;

public final class FunctionCall implements Serializable {


    private String functionCalled;
    private String functionParameters;
    private String functionReturn;


    public static void record(String nameOfTheFunctionCalled, FunctionCallParameters regExToMatchFunctionParametersSerializedAsJson, Object functionReturnAsJsonString) {

        String jsonReturn = functionReturnAsJsonString instanceof String ? (String) functionReturnAsJsonString : getAsJsonString(functionReturnAsJsonString);

        // TODO parameters.getStringRepresentationOfParameters() is BEFORE calls
        // TODO parameters.getParameters() has parameters after call - may not have the same StringRepresentationOfParameters AFTER call for IMPURE FUNCTION
        stubFor(post(urlMatching("/" + nameOfTheFunctionCalled))
                .withRequestBody(equalTo(regExToMatchFunctionParametersSerializedAsJson.getStringRepresentationOfParameters()))
                .willReturn(aResponse().withBody(jsonReturn).withStatus(200)));

    }

    public static void record(String functionName, FunctionCallParameters parameters) {

        // TODO parameters.getStringRepresentationOfParameters() is BEFORE calls
        // TODO parameters.getParameters() has parameters after call - may not have the same StringRepresentationOfParameters AFTER call for IMPURE FUNCTION
        stubFor(post(urlMatching("/" + functionName))
                .withRequestBody(equalToJson(parameters.getStringRepresentationOfParameters()))
                .willReturn(aResponse().withStatus(204)));

    }



    // TODO initally rename "expect"
    // TODO then provide mockito style alternative when X.y then return X
    public static FunctionCall create(String nameOfTheFunctionCalled, String regExToMatchFunctionParametersSerializedAsJson, String functionReturnAsJsonString) {

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

        //  TODO don't really need a return anymore
        return new FunctionCall(
                nameOfTheFunctionCalled,
                regExToMatchFunctionParametersSerializedAsJson,
                functionReturnAsJsonString);
    }

    private FunctionCall(String functionCalled, String functionParametersAsJsonString) {
        this.functionCalled = functionCalled;
        this.functionParameters = functionParametersAsJsonString;
    }

    private FunctionCall(String functionCalled, String functionParametersAsJsonString, String functionReturnAsJsonString) {
        this.functionCalled = functionCalled;
        this.functionParameters = functionParametersAsJsonString;
        this.functionReturn = functionReturnAsJsonString;
    }


    public FunctionCall(String functionCalled, FunctionCallParameters functionParameters) {
        this(functionCalled, functionParameters.getStringRepresentationOfParameters());
    }

    public FunctionCall(String functionCalled, FunctionCallParameters functionParameters, Object functionReturn) {
        this(
                functionCalled,
                functionParameters.getStringRepresentationOfParameters(),
                functionReturn instanceof String ? (String) functionReturn : getAsJsonString(functionReturn)
        );
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

    /**
     @deprecated
     */
    public String toJavaString(Function<String, String> stringConverterForExpectedParametersRegexAsJsonString, Function<String, String> stringConverterForReturnValueAsJsonString) {
        final StringBuilder sb = new StringBuilder("FunctionCall.create(\"");
        sb.append(functionCalled).append("\", \"");
        sb.append(stringConverterForExpectedParametersRegexAsJsonString.apply(functionParameters)).append("\", \"");
        sb.append(stringConverterForReturnValueAsJsonString.apply(functionReturn)).append("\"),");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "FunctionCall{" +
                "functionCalled='" + functionCalled + '\'' +
                ", \nfunctionParameters='" + functionParameters + '\'' +
                ", \nfunctionReturn='" + functionReturn + '\'' +
                '}';
    }

}
