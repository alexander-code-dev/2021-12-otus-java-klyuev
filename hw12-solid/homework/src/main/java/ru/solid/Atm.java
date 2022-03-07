package ru.solid;

import ru.solid.Impl.Cash;

public interface Atm {
    Cash takeCash(int reqAnAmount);
    boolean giveCash(Cash cash);
    int getBalance();
}