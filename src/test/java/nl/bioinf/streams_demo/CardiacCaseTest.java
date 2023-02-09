package nl.bioinf.streams_demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardiacCaseTest {

    @Test
    void fromString() {
        CardiacCase aCase = CardiacCase.fromString("92;103;86;40;100;85;0;1");
        System.out.println(aCase);
        //bhr, basebp, pkhr, sbp, dose, maxhr, age, gender, anyEvent
        assertEquals(aCase.bhr(), 92);
        assertEquals(aCase.basebp(), 103);
        assertEquals(aCase.sbp(), 86);
        assertEquals(aCase.dose(), 40);
        assertEquals(aCase.maxhr(), 100);
        assertEquals(aCase.age(), 85);
        assertSame(aCase.gender(), Gender.MALE);
        assertFalse(aCase.anyEvent());
    }

    @Test
    void toCsv() {
        String caseInput = "92;103;86;40;100;85;0;1";
        CardiacCase aCase = CardiacCase.fromString(caseInput);

        String csv1 = aCase.toCsv();
        System.out.println(csv1);
        assertEquals(caseInput, csv1);

        String newSep = ",";
        String csv2 = aCase.toCsv(newSep);
        System.out.println(csv2);
        assertEquals(caseInput.replaceAll(";", newSep), csv2);
    }
}