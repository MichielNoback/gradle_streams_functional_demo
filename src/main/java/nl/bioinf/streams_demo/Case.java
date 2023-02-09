package nl.bioinf.streams_demo;

/**
 * This record class models a row from the cardiac data file.
 *
 * bhr;basebp;sbp;dose;maxhr;age;gender;any event
 * 92;103;86;40;100;85;0;1
 * @param bhr the basal heart rate
 * @param basebp the basal blood pressure
 * @param sbp the systolic blood pressure
 * @param dose the dose of dobutamine given
 * @param maxhr the maximum heart rate
 * @param age the age
 * @param gender the gender
 * @param anyEvent whether any heart event occurred (death, infarction, angioplasty, or bypass)
 */
public record Case(int bhr, int basebp, int sbp, int dose, int maxhr, int age, Gender gender, boolean anyEvent) {

    /**
     * Factory method to construct from a single line of the csv file.
     * @param line the line to parse
     * @return aCase, a Case object
     */
    public static Case fromString(String line) {
        String[] elements = line.split(";");
        if (elements.length != 8 ) {
            throw new IllegalArgumentException("probably wrong file to parse: " + line);
        }
        try {
            int bhr = Integer.parseInt(elements[0]);
            int basebp = Integer.parseInt(elements[1]);
            int sbp = Integer.parseInt(elements[2]);
            int dose = Integer.parseInt(elements[3]);
            int maxhr = Integer.parseInt(elements[4]);
            int age = Integer.parseInt(elements[5]);
            Gender gender = Gender.fromCode(elements[6]);
            boolean anyEvent = (elements[7].equals("0"));

            return new Case(bhr, basebp, sbp, dose, maxhr, age, gender, anyEvent);
        } catch (NumberFormatException ex) {
            System.err.println("line");
            throw ex;
        }
    }

    /**
     * Overloaded utility method to write back to csv.
     * @param sep the separator to use.
     * @return csv representation.
     */
    public String toCsv(String sep) {
        char gndr = gender == Gender.MALE ? '0' : '1';
        char any = anyEvent ? '0' : '1';
        return bhr + sep + basebp + sep + sbp + sep + dose + sep + maxhr + sep + age + sep + gndr + sep + any;
    }

    /**
     * Overloaded utility method which defaults the separator to a semicolon.
     * @return the csv representation
     */
    public String toCsv() {
        return toCsv(";");
    }
}
