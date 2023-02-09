package nl.bioinf.streams_demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenderTest {
    @Test
    @DisplayName("tests normal use of Gender factory")
    void fromCode_normal() {
        Gender gender = Gender.fromCode("0");
        assertSame(Gender.MALE, gender);
        gender = Gender.fromCode("1");
        assertSame(Gender.FEMALE, gender);
    }

    @Test
    void fromCode_illegal() {
        assertThrows(IllegalArgumentException.class, () -> Gender.fromCode("2"));
    }

    @Test
    void fromCode_null() {
        assertThrows(IllegalArgumentException.class, () -> Gender.fromCode(null));
    }
}