package wintermock;

import static org.lazyluke.wintermock.FunctionCallParameters.params;

import wintermock.testclasses.ComplexType;
import wintermock.testclasses.Person;

public class InterfaceToMockStubPlaybackImplementation extends BaseStubImpl implements InterfaceToMock {
    public Person pureFunctionReturningComplexType(String stringParam, boolean primitiveBooleanParam, ComplexType c1) {

        return checkNextExpectedCallAndReturn(params(stringParam, primitiveBooleanParam, c1), Person.class);
    }
}
