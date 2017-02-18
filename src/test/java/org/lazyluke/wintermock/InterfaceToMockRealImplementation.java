package org.lazyluke.wintermock;

import org.lazyluke.wintermock.testclasses.ComplexType;
import org.lazyluke.wintermock.testclasses.Person;

public class InterfaceToMockRealImplementation implements InterfaceToMock {
    public Person pureFunctionReturningComplexType(String stringParam, boolean primitiveBooleanParam, ComplexType c1) {
        return c1.getEmployees().get(5);
    }
}
