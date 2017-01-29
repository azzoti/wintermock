package org.lazyluke.wintermock;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.lazyluke.wintermock.testclasses.*;

import java.math.BigDecimal;
import java.util.*;

public class TestDataMaker {


    public static ComplexType newComplexType() {
        BasicFieldsType basicFields = newBasicFieldsType();
        DateFieldsType dateFields = newDateFields();
        List<String> stringList = newStringList();
        String[] stringArray = newStringArray();
        List<Person> employees = newPersonList();
        Person boss = employees.get(2);
        Map<Person, List<House>> housing = newPersonHousingArrangements(employees);
        HousingArrangementsMap alternativeHousing = newHousingArrangementsMap(employees);
        return new ComplexType(basicFields, dateFields, stringList, stringArray, boss, employees, housing, alternativeHousing);
    }

    private static Map<Person, List<House>> newPersonHousingArrangements(List<Person> employees) {
        HashMap<Person, List<House>> m = new LinkedHashMap<>();
        m.put(employees.get(0), newHouses());
        m.put(employees.get(1), null);
        m.put(employees.get(2), newHouses("Main Residence", "In Town", "The Holiday Cottage", "Nice Village"));
        m.put(employees.get(3), newHouses("Flat 1", "London Street"));
        m.put(employees.get(4), newHouses("Town House", "Leith"));
        m.put(employees.get(5), newHouses());
        return m;
    }

    private static HousingArrangementsMap newHousingArrangementsMap(List<Person> employees) {
        HousingArrangementsMap m = new HousingArrangementsMap();
        m.put(employees.get(0), newHouses());
        m.put(employees.get(1), null);
        m.put(employees.get(2), newHouses("The Hilton", "Bath"));
        m.put(employees.get(3), newHouses("Humble Abode", "Denmark"));
        m.put(employees.get(4), null);
        m.put(employees.get(5), newHouses());
        return m;
    }


    private static List<House> newHouses(String... details) {
        ArrayList<House> houses = new ArrayList<>();
        for (int i = 0; i < details.length; i += 2) {
            houses.add(newHouse(details[i], details[i + 1]));
        }
        return houses;
    }

    private static House newHouse(String name, String address) {
        return new House(name, address);
    }

    private static Person newBossPerson() {
        return newPerson("TheBoss", 123, ColorSimpleEnum.HAZEL, CountryWithCodeEnum.FRANCE);
    }

    private static List<Person> newPersonList() {
        List<Person> people = new ArrayList<Person>();
        people.add(newPerson("Jane Smith", 23, ColorSimpleEnum.HAZEL, CountryWithCodeEnum.FRANCE));
        people.add(newPerson("John Jones", 24, ColorSimpleEnum.BLACK, CountryWithCodeEnum.UNITED_KINGDOM));
        people.add(newPerson("Daisy Macarthur", 54, ColorSimpleEnum.BLUE, CountryWithCodeEnum.UNITED_KINGDOM));
        people.add(newPerson("Danny Evans", 21, ColorSimpleEnum.RED, CountryWithCodeEnum.UNITED_KINGDOM));
        people.add(newPerson("Stuart Daniels", 45, ColorSimpleEnum.GREEN, CountryWithCodeEnum.UNITED_STATES));
        people.add(newPerson("Joan Collins", 91, ColorSimpleEnum.RED, CountryWithCodeEnum.FRANCE));
        return people;
    }

    private static String[] newStringArray() {
        return new String[]{"arrayElement1", "arrayElement2", null, "arrayElement4"};
    }

    private static ArrayList<String> newStringList() {
        ArrayList<String> simpleListOfStrings = new ArrayList<String>();
        simpleListOfStrings.add("listElement1");
        simpleListOfStrings.add("listElement2");
        simpleListOfStrings.add(null);
        simpleListOfStrings.add("listElement4");
        return simpleListOfStrings;
    }

    private static BasicFieldsType newBasicFieldsType() {
        return new BasicFieldsType(Integer.MAX_VALUE, Integer.MIN_VALUE,
                "A String {value} with embedded json [characters] in it ",
                false, true, Double.MAX_VALUE, Double.MIN_VALUE, BigDecimal.TEN.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public static Person newPerson(String personName, int age, ColorSimpleEnum eyeColor, CountryWithCodeEnum nationality) {
        return new Person(personName, age, eyeColor, nationality);
    }

    private static DateFieldsType newDateFields() {
        Calendar calendar = new GregorianCalendar(2013, 1, 28, 13, 24, 56);
        calendar.set(Calendar.MILLISECOND, 123456);
        Date javaDateField = new Date(calendar.getTimeInMillis());
        LocalDate localDate = LocalDate.fromCalendarFields(calendar);
        DateTime dateTime = null;
        return new DateFieldsType(javaDateField, localDate, dateTime, calendar);
    }
}
