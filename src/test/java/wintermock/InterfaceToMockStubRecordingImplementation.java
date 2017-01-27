package wintermock;

import static org.lazyluke.wintermock.FunctionCallParameters.params;

import wintermock.testclasses.ComplexType;
import wintermock.testclasses.Person;

public class InterfaceToMockStubRecordingImplementation extends BaseStubImpl implements InterfaceToMock {

    private InterfaceToMockRealImplementation delegate = new InterfaceToMockRealImplementation();


    public Person pureFunctionReturningComplexType(String stringParam, boolean primitiveBooleanParam, ComplexType c1) {


        return addFunctionCall(params(stringParam, primitiveBooleanParam, c1), delegate.pureFunctionReturningComplexType(stringParam, primitiveBooleanParam, c1));
    }
}
