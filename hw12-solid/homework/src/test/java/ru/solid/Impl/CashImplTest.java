package ru.solid.Impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.solid.Cash;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CashImplTest {

    @Test
    @DisplayName("Проверка строителя объекта деньги в одну пачку")
    void cashBuilderTest() {
        Cash money;

        money = new CashImpl.СhooseBanknotes().set();
        assertThat(money.getSum()).isEqualTo(0);

        money = new CashImpl.СhooseBanknotes().cellCash(50, 4).set();
        assertThat(money.getSum()).isEqualTo(200);

        money = new CashImpl.СhooseBanknotes().cellCash(50, 4).cellCash(100, 4).set();
        assertThat(money.getSum()).isEqualTo(600);
    }
}
