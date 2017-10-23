package com.rebolj.numbers.speller;

import javax.annotation.Nonnull;

public interface NumberSpeller {
    @Nonnull
    String spell(long value);
}
