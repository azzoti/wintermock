package wintermock.testclasses;

public class Person {

    public Person(String name, int legs) {
        this.name = name;
        this.age = legs;
    }

    private String name;
    private int age;
    private ColorSimpleEnum color;
    private CountryWithCodeEnum country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
