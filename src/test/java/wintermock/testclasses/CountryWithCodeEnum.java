package wintermock.testclasses;

public enum CountryWithCodeEnum {
    UNITED_KINGDOM("GBR"), UNITED_STATED("USA"), FRANCE("FRA");

    private String code;

    CountryWithCodeEnum(String code) {
        this.code = code;
    }
}
