package com.rebolj.numbers.speller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private final String INVALID_VALUE = "The input argument needs to be an integer between 0 and " + Long.MAX_VALUE + " inclusive.\n";
    private ByteArrayOutputStream out;

    @BeforeEach
    void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Test
    void validNumber() {
        App.main(new String[]{"101"});
        assertEquals("one hundred one\n", out.toString());
    }

    @Test
    void negativeNumber() {
        App.main(new String[]{"-99"});
        assertEquals(INVALID_VALUE, out.toString());
    }

    @Test
    void notNumber() {
        App.main(new String[]{"foobar"});
        assertEquals(INVALID_VALUE, out.toString());
    }

    @Test
    void noArgument() {
        App.main(new String[]{});
        assertEquals(INVALID_VALUE, out.toString());
    }
}