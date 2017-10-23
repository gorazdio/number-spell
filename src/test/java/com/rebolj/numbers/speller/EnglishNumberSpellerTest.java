package com.rebolj.numbers.speller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnglishNumberSpellerTest {
    @ParameterizedTest(name = "{0} should spell {1}")
    @CsvFileSource(resources = "/test-samples.csv")
    void spell(long value, String spelling) {
        final NumberSpeller numberSpeller = new EnglishNumberSpeller();
        assertEquals(spelling, numberSpeller.spell(value));
    }

    @ParameterizedTest(name = "{0} should be split into {1}")
    @CsvSource({"1,1", "12,12", "123,123", "1234,1.234", "12345,12.345", "123456,123.456", "1234567,'1.234.567"})
    void toTriplets(int in, String out) {
        final EnglishNumberSpeller numberSpeller = new EnglishNumberSpeller();
        assertEquals(out, numberSpeller.toTriplets(in).stream().map(String::valueOf).collect(Collectors.joining(".")));
    }

    @Test
    void appendSeparator() {
        final EnglishNumberSpeller numberSpeller = new EnglishNumberSpeller();
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
        final EnglishNumberSpeller numberSpeller = new EnglishNumberSpeller();

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