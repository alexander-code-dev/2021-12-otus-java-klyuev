package ru.solid.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.solid.Atm;
import ru.solid.exception.AtmTakeCashException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AtmImplTest {

    private Atm atm;
    private Cash moneyIn1;
    private Cash moneyIn2;
    private Cash moneyIn3;

    @BeforeEach
    public void init() {
        this.atm = new AtmImpl();
        this.moneyIn1 = new Cash.СhooseBanknotes()
                .fifeThousandRurCount(2)
                .oneThousandRurCount(1)
                .fiftyRurCount(2)
                .tenRurCount(10)
                .fiveRurCount(500)
                .set();
        this.moneyIn2 = new Cash.СhooseBanknotes().set();
        this.moneyIn3 = new Cash.СhooseBanknotes()
                .fifeThousandRurCount(1)
                .twoThousandRurCount(1)
                .oneThousandRurCount(1)
                .fifeHundredRurCount(1)
                .twoHundredRurCount(1)
                .hundredRurCount(1)
                .fiftyRurCount(1)
                .tenRurCount(1)
                .fiveRurCount(1)
                .set();
    }

    @Test
    @DisplayName("Выдача определенной суммы с АТМ")
    void takeCash() {
        atm.giveCash(moneyIn1);
        assertThat(atm.getBalance()).isEqualTo(13700);
        Cash moneyOut = atm.takeCash(6215);
        assertThat(moneyOut.getSum()).isEqualTo(6215);
        assertThat(moneyOut.getFifeThousandRurCount()).isEqualTo(1);
        assertThat(moneyOut.getOneThousandRurCount()).isEqualTo(1);
        assertThat(moneyOut.getFiftyRurCount()).isEqualTo(2);
        assertThat(moneyOut.getTenRurCount()).isEqualTo(10);
        assertThat(moneyOut.getFiveRurCount()).isEqualTo(3);
    }

    @Test
    @DisplayName("Пробуем получить больше чем есть в АТМ")
    void takeCashExceptionGetLargeSum() {
        atm.giveCash(moneyIn1);
        Throwable exception = assertThrows(AtmTakeCashException.class,() -> atm.takeCash(14000));
        assertThat(exception.getMessage()).isEqualTo("Запрошенная сумма превышает остаток");
    }

    @Test
    @DisplayName("Пробуем получить не кратную сумму по купюрам")
    void takeCashExceptionWrongAmount() {
        atm.giveCash(moneyIn1);
        Throwable exception = assertThrows(AtmTakeCashException.class,() -> atm.takeCash(151));
        assertThat(exception.getMessage()).isEqualTo("Не возможно выдать сумму" +
                ", запрошенная сумма некорректна или в АТМ недостаточно кратных купюр");
    }

    @Test
    @DisplayName("Не кладем купюры в АТМ")
    void giveCash() {
        boolean f = atm.giveCash(moneyIn2);
        assertThat(f).isEqualTo(false);
        assertThat(atm.getBalance()).isEqualTo(0);
    }

    @Test
    @DisplayName("Положили по одной (каждой) купюре")
    void getCashBalance() {
        atm.giveCash(moneyIn3);
        assertThat(atm.getBalance()).isEqualTo(8865);
    }
}