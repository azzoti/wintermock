package org.lazyluke.wintermock;

import org.lazyluke.wintermock.testclasses.ComplexType;
import org.lazyluke.wintermock.testclasses.Person;
public interface InterfaceToMock {

//    String simpleFunctionWithoutParametersReturningString();
//
//    int simpleFunctionWithoutParametersReturningPrimitiveInt();
//
//    Integer simpleFunctionWithoutParametersReturningInteger();
//
//    boolean simpleFunctionWithoutParametersReturningPrimitiveBoolean();
//
//    Boolean simpleFunctionWithoutParametersReturningBoolean();
//
//    ComplexType simpleFunctionWithoutParametersReturningComplexType();
//
//    ComplexType[] simpleFunctionWithoutParametersReturningArrayOfComplexType();
//
//    List<ComplexType> simpleFunctionWithoutParametersReturningListOfComplexType();

    Person pureFunctionReturningComplexType(String stringParam, boolean primitiveBooleanParam, ComplexType c1);

//    Map<Person, List<ComplexType>> pureFunctionReturningComplexMap(
//            String s1, String s2, int i1, Integer i2, boolean b1, Boolean b2, ComplexType c1, ComplexType c2);
//
//    Map<Person, List<ComplexType>> impureFunctionReturningComplexMapWithSideEffectsOnParameters(
//            String s1, String s2, int i1, Integer i2, boolean b1, Boolean b2, ComplexType c1, ComplexType c2);


}
