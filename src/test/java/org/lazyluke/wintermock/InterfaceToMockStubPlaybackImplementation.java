package org.lazyluke.wintermock;

import static org.lazyluke.wintermock.FunctionCallParameters.params;

import org.lazyluke.wintermock.testclasses.ComplexType;
import org.lazyluke.wintermock.testclasses.Person;
import org.lazyluke.wintermock.wintermock.BaseStubImpl;

public class InterfaceToMockStubPlaybackImplementation extends BaseStubImpl implements InterfaceToMock {
    public Person pureFunctionReturningComplexType(String stringParam, boolean primitiveBooleanParam, ComplexType c1) {

        return checkNextExpectedCallAndReturn(params(stringParam, primitiveBooleanParam, c1), Person.class);
    }
}
