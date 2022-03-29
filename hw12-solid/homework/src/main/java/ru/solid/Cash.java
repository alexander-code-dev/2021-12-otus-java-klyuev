package ru.solid;

import java.util.List;

public interface Cash extends SumMoney {
    List<Cell> getCells();
}
