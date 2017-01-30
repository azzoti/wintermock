package org.lazyluke.wintermock;

import com.fasterxml.jackson.core.type.TypeReference;

public abstract class BaseStubImpl {

    public <T> T checkNextExpectedCallAndReturn(FunctionCallParameters actualFunctionParameters, Class<T> classOfReturn) {
        // Be very careful about moving this line to any other class or method
        String methodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        return FunctionCalls.checkNextExpectedCallAndReturn(methodName, actualFunctionParameters, classOfReturn);
    }

    public <T> T checkNextExpectedCallAndReturn(FunctionCallParameters actualFunctionParameters, TypeReference<T> typeRefOfReturn) {
        // Be very careful about moving this line to any other class or method
        String methodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        return FunctionCalls.checkNextExpectedCallAndReturn(methodName, actualFunctionParameters, typeRefOfReturn);
    }

    protected void addFunctionCall(FunctionCallParameters parameters) {
        // Be very careful about moving this line to any other class or method
        String methodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        FunctionCalls.addFunctionCall(methodName, parameters);
    }

    protected <T> T addFunctionCall(FunctionCallParameters parameters, T returnValue) {
        // Be very careful about moving this line to any other class or method
        String methodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        return FunctionCalls.addFunctionCall(methodName, parameters, returnValue);
    }

}
