package org.lazyluke.wintermock.wintermock;

import org.lazyluke.wintermock.FunctionCallParameters;
import org.lazyluke.wintermock.FunctionCallsManager;
import com.fasterxml.jackson.core.type.TypeReference;

public abstract class BaseStubImpl {

    private FunctionCallsManager functionCallsManager = new FunctionCallsManager();

    public <T> T checkNextExpectedCallAndReturn(FunctionCallParameters actualFunctionParameters, Class<T> classOfReturn) {
        // Be very careful about moving this line to any other class or method
        String methodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        return functionCallsManager.getFunctionCalls().checkNextExpectedCallAndReturn(methodName, actualFunctionParameters, classOfReturn);
    }

    public <T> T checkNextExpectedCallAndReturn(FunctionCallParameters actualFunctionParameters, TypeReference<T> typeRefOfReturn) {
        // Be very careful about moving this line to any other class or method
        String methodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        return functionCallsManager.getFunctionCalls().checkNextExpectedCallAndReturn(methodName, actualFunctionParameters, typeRefOfReturn);
    }

    protected void addFunctionCall(FunctionCallParameters parameters) {
        // Be very careful about moving this line to any other class or method
        String methodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        functionCallsManager.getFunctionCalls().addFunctionCall(methodName, parameters);
    }

    protected <T> T addFunctionCall(FunctionCallParameters parameters, T returnValue) {
        // Be very careful about moving this line to any other class or method
        String methodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        return functionCallsManager.getFunctionCalls().addFunctionCall(methodName, parameters, returnValue);
    }

}
