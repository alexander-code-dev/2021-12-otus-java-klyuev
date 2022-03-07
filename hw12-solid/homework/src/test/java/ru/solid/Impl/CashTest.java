package ru.solid.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CashTest {

    private Cash money;

    @BeforeEach
    public void init() {
        this.money = new Cash.СhooseBanknotes()
                .fifeThousandRurCount(2)
                .oneThousandRurCount(1)
                .fiftyRurCount(2)
                .tenRurCount(10)
                .fiveRurCount(500)
                .set();
    }

    @Test
    @DisplayName("Проверка строителя объекта деньги в одну пачку")
    void cashBuilderTest() {
        assertThat(money.getFiveRurCount()).isEqualTo(500);
        assertThat(money.getTenRurCount()).isEqualTo(10);
        assertThat(money.getFiftyRurCount()).isEqualTo(2);
        assertThat(money.getHundredRurCount()).isEqualTo(0);
        assertThat(money.getTwoHundredRurCount()).isEqualTo(0);
        assertThat(money.getFifeHundredRurCount()).isEqualTo(0);
        assertThat(money.getOneThousandRurCount()).isEqualTo(1);
        assertThat(money.getTwoThousandRurCount()).isEqualTo(0);
        assertThat(money.getFifeThousandRurCount()).isEqualTo(2);
        assertThat(money.getSum()).isEqualTo(13700);
    }
}