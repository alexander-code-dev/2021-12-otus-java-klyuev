package ru.solid;

public interface Atm {
    Cash takeCash(int reqAnAmount);
    void putCash(Cash cash);
    int getBalance();
}