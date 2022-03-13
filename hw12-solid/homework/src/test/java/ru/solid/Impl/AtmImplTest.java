package ru.solid.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.solid.Atm;
import ru.solid.Cash;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AtmImplTest {

    private Atm atm;

    @BeforeEach
    void init() {
        this.atm = new AtmImpl();
    }

    @Test
    @DisplayName("Прием купюр АТМ")
    void giveCash() {
        Cash cash1 = new CashImpl.СhooseBanknotes()
                .cellCash(50, 4)
                .cellCash(200, 5)
                .set();
        this.atm.giveCash(cash1);

        assertThat(this.atm.getBalance()).isEqualTo(cash1.getSum());

        Cash cash2 = new CashImpl.СhooseBanknotes()
                .cellCash(50, 40)
                .cellCash(200, 50)
                .set();
        this.atm.giveCash(cash2);


        assertThat(this.atm.getBalance()).isEqualTo(cash1.getSum() + cash2.getSum());

        Cash cashOut = this.atm.takeCash(cash1.getSum() + cash2.getSum());

        assertThat(atm.getBalance()).isEqualTo(0);
        assertThat(cashOut.getSum()).isEqualTo(cash1.getSum() + cash2.getSum());
    }

    @Test
    @DisplayName("Запрос баланса в АТМ")
    void getCashBalance() {
        Cash cash1 = new CashImpl.СhooseBanknotes()
                .cellCash(2000, 4)
                .cellCash(50, 4)
                .cellCash(200, 5)
                .set();

        assertThat(atm.getBalance()).isEqualTo(0);

        atm.giveCash(cash1);

        assertThat(atm.getBalance()).isEqualTo(cash1.getSum());

        Cash cash2 = new CashImpl.СhooseBanknotes()
                .cellCash(5, 40)
                .cellCash(100, 14)
                .cellCash(500, 7)
                .cellCash(200, 3)
                .set();

        assertThat(atm.getBalance()).isEqualTo(cash1.getSum());

        atm.giveCash(cash2);

        assertThat(atm.getBalance()).isEqualTo(cash1.getSum() + cash2.getSum());
    }

    @Test
    @DisplayName("Выдача суммы АТМ")
    void takeCash() {
        Cash cash = new CashImpl.СhooseBanknotes()
                .cellCash(50, 40)
                .cellCash(2000, 4)
                .cellCash(10, 40000)
                .cellCash(200, 50)
                .set();
        atm.giveCash(cash);

        assertThat(cash.getSum()).isEqualTo(atm.getBalance());

        int reqAnAmount = 42100;
        atm.takeCash(reqAnAmount);

        assertThat(atm.getBalance()).isEqualTo(cash.getSum() - reqAnAmount);
    }

    /*private Cash moneyIn2;
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
    }*/
}
