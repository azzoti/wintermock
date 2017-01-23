package wintermock.testclasses;

public class Person {

    private String name;
    private int age;
    private ColorSimpleEnum eyeColor;
    private CountryWithCodeEnum nationality;

    public Person(String name, int age, ColorSimpleEnum color, CountryWithCodeEnum country) {
        this.name = name;
        this.age = age;
        this.eyeColor = color;
        this.nationality = country;
    }

    public Person() {
    }

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

    public ColorSimpleEnum getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(ColorSimpleEnum eyeColor) {
        this.eyeColor = eyeColor;
    }

    public CountryWithCodeEnum getNationality() {
        return nationality;
    }

    public void setNationality(CountryWithCodeEnum nationality) {
        this.nationality = nationality;
    }
}
