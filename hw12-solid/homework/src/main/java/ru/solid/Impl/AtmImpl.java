package ru.solid.Impl;

import ru.solid.Atm;
import ru.solid.Cash;
import ru.solid.Cell;
import ru.solid.cashType.Banknotes;
import ru.solid.exception.AtmTakeCashException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AtmImpl implements Atm {

    private List<Cell> cells = new ArrayList<>();

    public AtmImpl() { }

    public Cash takeCash(int reqAnAmount) {
        int cash = 0;
        Banknotes[] banknotes = Banknotes.values();
        List<Cell> cellsForIssuance = new ArrayList<>();
        List<Cell> atmCells = this.cells;

        if (this.getBalance() < reqAnAmount) {
            throw new AtmTakeCashException("Запрошенная сумма выше чем актуальный баланс");
        } else if (reqAnAmount % banknotes[0].getDenomination() != 0) {
            throw new AtmTakeCashException("Не кратная сумма для выдачи, минимальная купюра "
                    +banknotes[0].getDenomination()
                    +" RUR");
        }

        for (int i = banknotes.length-1; i != 0; i--) {
            for (int j = 0; j < atmCells.size() && cash < reqAnAmount; j++) {
                if (atmCells.get(j).getDenomination() == banknotes[i].getDenomination()
                        && reqAnAmount >= banknotes[i].getDenomination()) {
                    int count = 0;
                    while ((reqAnAmount - cash) >= atmCells.get(j).getDenomination()
                            && cash < reqAnAmount
                            && atmCells.get(j).getCount() > count) {
                        cash+=banknotes[i].getDenomination();
                        count++;
                    }
                    cellsForIssuance.add(new CellImpl.CreateBanknotes()
                            .putMoneySameDenomination(banknotes[i].getDenomination(), count).set());
                }
            }
        }
        if (reqAnAmount != cash) {
            throw new AtmTakeCashException("Для выдачи нужной суммы в АТМ не достаточно купюр малого номинала");
        } else {
            this.cells = this.AtmCellsMinusCellsForIssuance(atmCells, cellsForIssuance);
        }
        return new CashImpl.СhooseBanknotes().cellsCash(cellsForIssuance).set();
    }

    private List<Cell> AtmCellsMinusCellsForIssuance(List<Cell> atmCells, List<Cell> cellsForIssuance) {
        for (int i = 0; atmCells.size() > i; i++) {
            for (int j = 0; cellsForIssuance.size() > j; j++) {
                if (atmCells.get(i).getDenomination() == cellsForIssuance.get(j).getDenomination()) {
                    Cell cell = atmCells.get(i);
                    cell.setCount(atmCells.get(i).getCount() - cellsForIssuance.get(j).getCount());
                    atmCells.set(i, cell);
                }
            }
        }
        return atmCells;
    }

    private List<Cell> doCompareCells(List<Cell> cells) {
        cells.sort(new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                if (o1.getDenomination() == o2.getDenomination()) {
                    return 0;
                }
                return o1.getDenomination() > o2.getDenomination() ? -1 : 1;
            }
        });
        return cells;
    }

    public void giveCash(Cash cash) {
        List<Cell> tempSells = new ArrayList<>();
        List<Cell> comparedSells = new ArrayList<>();
        tempSells.addAll(this.cells);
        tempSells.addAll(new CashImpl.СhooseBanknotes().cellsCash(cash.getCells()).set().getCells());
        tempSells = this.doCompareCells(tempSells);
        int denomination = -1;
        for (int i = 0; i < tempSells.size(); i++) {
            if (tempSells.get(i).getDenomination() == denomination) {
                Cell cellSumCount = new CellImpl.CreateBanknotes()
                        .putMoneySameDenomination(denomination,
                                tempSells.get(i).getCount() + tempSells.get(i-1).getCount()
                        ).set();
                comparedSells.set(comparedSells.size()-1, cellSumCount);
            } else {
                comparedSells.add(tempSells.get(i));
                denomination = tempSells.get(i).getDenomination();
            }
        }

        this.cells = comparedSells;
    }

    @Override
    public int getBalance() {
        return this.cells.stream().mapToInt(Cell::getSum).sum();
    }

    @Override
    public String toString() {
        return "AtmImpl{" +
                "cells=" + cells +
                '}';
    }
}
