package ru.solid.Impl;

import ru.solid.Cash;
import ru.solid.Cell;

import java.util.ArrayList;
import java.util.List;

public class CashImpl implements Cash {
    private final List<Cell> cells;

    public static class СhooseBanknotes {
        private final List<Cell> cells = new ArrayList<>();

        public СhooseBanknotes cellCash(int denomination, int count) {
            Cell cell = new CellImpl.CreateBanknotes()
                    .putMoneySameDenomination(denomination, count)
                    .set();
            this.cells.add(cell);
            return this;
        }
        public СhooseBanknotes cellsCash(List<Cell> cells) {
            for (Cell cell:cells) {
                this.cellCash(cell.getDenomination(), cell.getCount());
            }
            return this;
        }
        public CashImpl set() {
            if (cells.size() == 0) {
                System.out.println("Вы забыли вложить купюры");
            }
            return new CashImpl(this);
        }
    }

    private CashImpl(СhooseBanknotes chooseBanknotes) {
        this.cells = chooseBanknotes.cells;
    }

    @Override
    public List<Cell> getCells() {
        return cells;
    }

    @Override
    public int getSum() {
        return this.cells.stream().mapToInt(Cell::getSum).sum();
    }

    @Override
    public String toString() {
        return "Cash{" +
                "cells=" + cells +
                '}';
    }
}
