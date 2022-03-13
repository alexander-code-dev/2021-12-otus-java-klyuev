package ru.solid.cashType;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BanknotesTest {

    @Test
    void values() {
        assertThat(Banknotes.ONE_THOUSAND.getDenomination()).isEqualTo(1000);
        assertThat(Arrays.stream(Banknotes.values()).filter(enumObj -> enumObj.getDenomination() == 1000).count())
                .isEqualTo(1);
    }
}