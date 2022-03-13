package ru.solid.Impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.solid.Sum;
import ru.solid.exception.AtmGiveCashException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CellImplTest {

    @Test
    @DisplayName("Проверка корректного вычисления суммы ячейки")
    void getSum() {
        Sum sum = new CellImpl.CreateBanknotes()
                .putMoneySameDenomination(50,10)
                .set();
        assertThat(sum.getSum()).isEqualTo(500);
    }

    @Test
    @DisplayName("Проверка на существования купюры")
    void getRealBanknotes() {
        int notRealBanknotes = 12;
        Throwable exception = assertThrows(AtmGiveCashException.class,
                () -> new CellImpl.CreateBanknotes().putMoneySameDenomination(notRealBanknotes,10).set());
        assertThat(exception.getMessage()).isEqualTo("Купюры номиналом: "+notRealBanknotes+" не существует");
    }
}