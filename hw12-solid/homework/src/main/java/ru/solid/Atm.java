package ru.solid;

public interface Atm {
    Cash takeCash(int reqAnAmount);
    void giveCash(Cash cash);
    int getBalance();
}