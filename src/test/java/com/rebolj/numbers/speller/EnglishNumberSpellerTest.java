package com.rebolj.numbers.speller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnglishNumberSpellerTest {
    private EnglishNumberSpeller numberSpeller;

    @BeforeEach
    void setUp() {
        numberSpeller = new EnglishNumberSpeller();
    }

    @ParameterizedTest(name = "{0} should spell {1}")
    @CsvFileSource(resources = "/test-samples.csv")
    void spell(long value, String spelling) {
        assertEquals(spelling, numberSpeller.spell(value));
    }

    @ParameterizedTest(name = "{0} should be split into {1}")
    @CsvSource({"1,1", "12,12", "123,123", "1234,1.234", "12345,12.345", "123456,123.456", "1234567,'1.234.567"})
    void toTriplets(int in, String out) {
        assertEquals(out, numberSpeller.toTriplets(in).stream().map(String::valueOf).collect(Collectors.joining(".")));
    }

    @Test
    void appendSeparator() {
        final StringBuilder builder = new StringBuilder();

        // don't append when no existing characters
        numberSpeller.appendSeparator("-", builder);
        assertEquals("", builder.toString());

        // apend when existing characters
        builder.append("x");
        numberSpeller.appendSeparator("-", builder);
        assertEquals("x-", builder.toString());
    }

    @Test
    void appendZero() {

        // append when no existing characters
        StringBuilder builder = new StringBuilder();
        numberSpeller.appendZero(builder);
        assertEquals("zero", builder.toString());

        // don't append when existing characters
        builder = new StringBuilder();
        builder.append("x");
        numberSpeller.appendZero(builder);
        assertEquals("x", builder.toString());
    }
}