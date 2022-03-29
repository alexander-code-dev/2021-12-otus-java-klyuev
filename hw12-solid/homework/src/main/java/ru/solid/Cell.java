package ru.solid;

public interface Cell extends SumMoney {
    int getDenomination();
    int getCount();
    void setCount(int count);
}
