package ru.solid.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.solid.Atm;
import ru.solid.Cash;
import ru.solid.Impl.AtmImpl;
import ru.solid.Impl.CashImpl;
import ru.solid.cashType.Banknotes;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AtmTakeCashExceptionTest {

    private Atm atm;

    @BeforeEach
    void init() {
        this.atm = new AtmImpl();
    }

    @Test
    @DisplayName("Запрос выдачи из АТМ больше чем есть")
    void takeCashExceptionTestOne() {
        Cash cash = new CashImpl.Banknotes()
                .cellCash(50, 4)
                .cellCash(200, 5)
                .build();
        this.atm.putCash(cash);
        Throwable exception = assertThrows(AtmTakeCashException.class,
                () -> this.atm.takeCash(this.atm.getBalance() + 100));
        assertThat(exception.getMessage()).isEqualTo("Запрошенная сумма выше чем актуальный баланс");
    }

    @Test
    @DisplayName("Запрос выдачи из АТМ не каратной суммы")
    void takeCashExceptionTestTwo() {
        Cash cash = new CashImpl.Banknotes()
                .cellCash(50, 4)
                .cellCash(200, 5)
                .build();
        this.atm.putCash(cash);
        Throwable exception = assertThrows(AtmTakeCashException.class,
                () -> this.atm.takeCash(3));
        assertThat(exception.getMessage()).isEqualTo("Не кратная сумма для выдачи, минимальная купюра "
                +Banknotes.values()[0].getDenomination()
                +" RUR");
    }

    @Test
    @DisplayName("Запрос выдачи из АТМ суммы которая достаточна по балансу, но для которой не достаточно купюр")
    void takeCashExceptionTestThree() {
        Cash cash = new CashImpl.Banknotes()
                .cellCash(50, 4)
                .cellCash(200, 5)
                .build();
        this.atm.putCash(cash);
        Throwable exception = assertThrows(AtmTakeCashException.class,
                () -> this.atm.takeCash(655));
        assertThat(exception.getMessage()).isEqualTo("Для выдачи нужной суммы в АТМ не достаточно купюр малого номинала");
    }
}