package com.rebolj.numbers.speller;

public class App {
    private static final String INVALID_INPUT = "The input argument needs to be an integer between 0 and " + Long.MAX_VALUE + " inclusive.";

    public static void main(String[] args) {
        final long value;
        try {
            value = Long.parseLong(args[0]);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println(INVALID_INPUT);
            return;
        }

        if (value < 0) { System.out.println(INVALID_INPUT); return; }
        System.out.println(new EnglishNumberSpeller().spell(value));
    }
}
