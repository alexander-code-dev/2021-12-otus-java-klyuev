package ru.solid;

import java.util.List;

public interface Cash extends Sum {
    List<Cell> getCells();
}
