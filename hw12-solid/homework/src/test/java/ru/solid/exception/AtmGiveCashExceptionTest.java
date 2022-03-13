package ru.solid.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.solid.Impl.CellImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AtmGiveCashExceptionTest {

    @Test
    @DisplayName("Не действительная купюра")
    void CellCreateBanknotesTest() {
        int denomination = 4;
        Throwable exception = assertThrows(AtmGiveCashException.class,
                () -> new CellImpl.CreateBanknotes().putMoneySameDenomination(denomination, 1).set());
        assertThat(exception.getMessage()).isEqualTo("Купюры номиналом: "+denomination+" не существует");
    }
}