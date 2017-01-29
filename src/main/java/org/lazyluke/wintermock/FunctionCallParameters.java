package org.lazyluke.wintermock;

import static java.util.Arrays.asList;
import static org.lazyluke.wintermock.StubbingHelper.getAsJsonString;

import java.util.List;
public class FunctionCallParameters {

    private String stringRepresentationOfParameters;
    private List<Object> parameters;


    public static FunctionCallParameters params(Object... parameters) {
        return getFunctionCallParameters(parameters);
    }

    private static FunctionCallParameters getFunctionCallParameters(Object[] parameters) {
        List<Object> pList = asList(parameters);
        return new FunctionCallParameters(getAsJsonString(pList), pList);
    }

    public String getStringRepresentationOfParameters() {
        return stringRepresentationOfParameters;
    }

    private FunctionCallParameters(String jsonString, List<Object> parameters) {
        this.stringRepresentationOfParameters = jsonString;
        this.parameters = parameters;
    }


}