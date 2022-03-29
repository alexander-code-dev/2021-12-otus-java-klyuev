package ru.solid.Impl;

import ru.solid.Cell;
import ru.solid.cashType.Banknotes;
import ru.solid.exception.AtmGiveCashException;

import java.util.Arrays;

public class CellImpl implements Cell {
    private final int denomination;
    private int count;

    private CellImpl (CreateBanknotes createBanknotes) {
        this.count = createBanknotes.count;
        this.denomination = createBanknotes.denomination;
    }

    @Override
    public int getDenomination() {
        return this.denomination;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getSum() {
        return this.denomination * this.count;
    }

    @Override
    public String toString() {
        return "CellImpl{" +
                "denomination=" + this.denomination +
                ", count=" + this.count +
                '}';
    }

    public static class CreateBanknotes{

        private int denomination;
        private int count;

        public CreateBanknotes putMoneySameDenomination(int denomination, int count) {
            if (Arrays.stream(Banknotes.values()).noneMatch(f -> f.getDenomination() == denomination)) {
                throw new AtmGiveCashException("Купюры номиналом: "+denomination+" не существует");
            }
            if (count == 0) {
                System.out.println("Вы забыли вложить купюры для номинала: "+ denomination);
            }
            this.denomination = denomination;
            this.count = count;
            return this;
        }
        public CellImpl set() {
            return new CellImpl(this);
        }
    }
}
