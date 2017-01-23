package wintermock;

import wintermock.testclasses.ComplexType;
import wintermock.testclasses.Person;

public class InterfaceToMockRealImplementation implements InterfaceToMock {
    public Person pureFunctionReturningComplexType(String stringParam, boolean primitiveBooleanParam, ComplexType c1) {
        return c1.getEmployees().get(5);
    }
}
