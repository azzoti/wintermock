package wintermock.testclasses;

public enum CountryWithCodeEnum {
    UNITED_KINGDOM("GBR"), UNITED_STATES("USA"), FRANCE("FRA");

    private String code;

    CountryWithCodeEnum(String code) {
        this.code = code;
    }
}
