package wintermock.testclasses;

import java.util.List;
import java.util.Map;

public class ComplexType {

    private BasicFieldsType basicFields;

    private DateFieldsType dateFields;

    private List<String> simpleListOfStrings;

    private String[] simpleArrayOfStrings;

    private Person boss;

    private List<Person> employees;

    private Map<Person,List<House>> housing;

    private HousingArrangementsMap alternativeHousing;

    public ComplexType(BasicFieldsType basicFields, DateFieldsType dateFields, List<String> simpleListOfStrings, String[] simpleArrayOfStrings,
                       Person boss,
                       List<Person> employees,
                       Map<Person, List<House>> housing,
                       HousingArrangementsMap alternativeHousing) {
        this.basicFields = basicFields;
        this.dateFields = dateFields;
        this.simpleListOfStrings = simpleListOfStrings;
        this.simpleArrayOfStrings = simpleArrayOfStrings;
        this.boss = boss;
        this.employees = employees;
        this.housing = housing;
        this.alternativeHousing = alternativeHousing;
    }


    public ComplexType() {
    }

    public BasicFieldsType getBasicFields() {
        return basicFields;
    }

    public void setBasicFields(BasicFieldsType basicFields) {
        this.basicFields = basicFields;
    }

    public DateFieldsType getDateFields() {
        return dateFields;
    }

    public void setDateFields(DateFieldsType dateFields) {
        this.dateFields = dateFields;
    }

    public List<String> getSimpleListOfStrings() {
        return simpleListOfStrings;
    }

    public void setSimpleListOfStrings(List<String> simpleListOfStrings) {
        this.simpleListOfStrings = simpleListOfStrings;
    }

    public String[] getSimpleArrayOfStrings() {
        return simpleArrayOfStrings;
    }

    public void setSimpleArrayOfStrings(String[] simpleArrayOfStrings) {
        this.simpleArrayOfStrings = simpleArrayOfStrings;
    }

    public Person getBoss() {
        return boss;
    }

    public void setBoss(Person boss) {
        this.boss = boss;
    }

    public List<Person> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Person> employees) {
        this.employees = employees;
    }

    public Map<Person, List<House>> getHousing() {
        return housing;
    }

    public void setHousing(Map<Person, List<House>> housing) {
        this.housing = housing;
    }

    public HousingArrangementsMap getAlternativeHousing() {
        return alternativeHousing;
    }

    public void setAlternativeHousing(HousingArrangementsMap alternativeHousing) {
        this.alternativeHousing = alternativeHousing;
    }
}
