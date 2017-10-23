package com.rebolj.numbers.speller;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class EnglishNumberSpeller implements NumberSpeller {

    private final static String[] UNITS = new String[]{"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private final static String[] TEENS = new String[]{"eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private final static String[] TENS = new String[]{"ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    private final static String QUINTILLION = "quintillion";
    private final static String QUADRILLION = "quadrillion";
    private final static String TRILLION = "trillion";
    private final static String BILLION = "billion";
    private final static String MILLION = "million";
    private final static String THOUSAND = "thousand";
    private final static String HUNDRED = " hundred";
    private final static String NUMBER_TOO_BIG = "Number too big";
    private final static String POSITIVE_ONLY = "Only positive numbers supported.";
    private final static String ZERO = "zero";

    /**
     * @param value a positive number
     * @return the English spelling of the value reprensented by value
     * @throws IllegalArgumentException if value is not positive
     */
    @Nonnull
    @Override
    public String spell(long value) {
        Preconditions.checkArgument(value >= 0, POSITIVE_ONLY);
        final StringBuilder builder = new StringBuilder();
        spellTriplets(toTriplets(value), builder);
        return builder.toString();
    }

    /**
     * Group number into threes of digits
     */
    List<Integer> toTriplets(final long value) {
                final List<Integer> triplets = new LinkedList<>();
        long curValue = value;

        while (curValue >= 1000) {
            triplets.add(0, (int)(curValue % 1000L));
            curValue = curValue / 1000L;
        }

        triplets.add(0, (int)curValue);
        return triplets;
    }

    private void spellTriplets(final List<Integer> triplets, final StringBuilder builder) {
        int power = triplets.size();
        for (Integer triplet : triplets) {
            spellTriplet(triplet, builder);
            switch (power) {
                case 7:
                    builder.append(" " + QUINTILLION);
                    break;
                case 6:
                    builder.append(" " + QUADRILLION);
                    break;
                case 5:
                    builder.append(" " + TRILLION);
                    break;
                case 4:
                    builder.append(" " + BILLION);
                    break;
                case 3:
                    builder.append(" " + MILLION);
                    break;
                case 2:
                    builder.append(" " + THOUSAND);
                    break;
                case 1:
                    break;
                default:
                    throw new IllegalArgumentException(NUMBER_TOO_BIG);
            }

            power--;
        }
    }

    private void spellTriplet(final int value, final StringBuilder builder) {
        int curValue = value;
        boolean tens = false;

        if (curValue >= 100) {
            appendSeparator(" ", builder);
            builder.append(UNITS[curValue / 100]).append(HUNDRED);
            curValue %= 100;
        }

        if (curValue >= 10) {
            appendSeparator(" ", builder);
            if (curValue < 20) {
                builder.append(TEENS[curValue%10 - 1]);
                curValue = 0;
            } else {
                builder.append(TENS[curValue/10 - 1]);
                curValue %= 10;
                tens = true;
            }
        }

        if (curValue > 0) {
            appendSeparator(tens ? "-" : " ", builder);
            builder.append(UNITS[curValue]);
        } else {
            appendZero(builder);
        }
    }

    /**
     * Append a separator id and only if there EXIST preceding characters in the builder
     */
    void appendSeparator(String separator, final StringBuilder builder) {
        if (builder.length() > 0) builder.append(separator);
    }

    /**
     * Append `zero`, if and only if there are NO preceding characters in the builder
     */
    void appendZero(final StringBuilder builder) {
        if (builder.length() == 0) builder.append(ZERO);
    }
}
