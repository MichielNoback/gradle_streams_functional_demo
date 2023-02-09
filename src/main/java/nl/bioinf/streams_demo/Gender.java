package nl.bioinf.streams_demo;

/**
 * Models the gender of the subjects.
 */
public enum Gender {
    FEMALE('f'),
    MALE('m');


    private final char code;

    Gender(char code) {
        this.code = code;
    }

    /**
     * Factory method to construct from csv file codes.
     * code `0` wil return MALE.
     * code `1` will return FEMALE.
     * All other codes will raise an exception.
     * @param code the code to convert
     * @return the enum instance
     */
    public static Gender fromCode(String code) {
        if (null == code) throw new IllegalArgumentException("code is null");
        if (code.equals("0")) return MALE;
        if (code.equals("1")) return FEMALE;
        throw new IllegalArgumentException("No such gender: " + code);
    }
}
