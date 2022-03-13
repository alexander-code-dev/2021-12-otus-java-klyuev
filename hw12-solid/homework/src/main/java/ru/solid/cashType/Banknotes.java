package ru.solid.cashType;

public enum Banknotes {

    FIVE(5),
    TEN(10),
    FIFTY(50),
    HUNDRED(100),
    TWO_HUNDRED(200),
    FIFE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIFE_THOUSAND(5000);

    private final int denomination;

    Banknotes(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }
}
