package ru.solid.Impl;

import ru.solid.Atm;
import ru.solid.exception.AtmTakeCashException;

public class AtmImpl implements Atm {

    private int fiveRurCount;
    private int tenRurCount;
    private int fiftyRurCount;
    private int hundredRurCount;
    private int twoHundredRurCount;
    private int fifeHundredRurCount;
    private int oneThousandRurCount;
    private int twoThousandRurCount;
    private int fifeThousandRurCount;

    public AtmImpl() { }

    @Override
    public Cash takeCash(int reqAnAmount) {

        int fiveRur = 0;
        int tenRur = 0;
        int fiftyRur = 0;
        int hundredRur = 0;
        int twoHundredRur = 0;
        int fifeHundredRur = 0;
        int oneThousandRur = 0;
        int twoThousandRur = 0;
        int fifeThousandRur = 0;
        int cash = 0;
        int amount = reqAnAmount;

        int[] cashArray = {fifeThousandRurCount, twoThousandRurCount, oneThousandRurCount, fifeHundredRurCount,
                           twoHundredRurCount, hundredRurCount, fiftyRurCount, tenRurCount, fiveRurCount};

        if (reqAnAmount > this.getBalance()) {
            throw new AtmTakeCashException("Запрошенная сумма превышает остаток");
        }

        for (int i = 0; i < cashArray.length; i++) {
            for (int j = 0; j < cashArray[i] && cash < reqAnAmount; j++) {
                if (i == 0 && amount >= 5000) { cash += 5000; this.fifeThousandRurCount--; fifeThousandRur++; amount-=5000; }
                else if (i == 1 && amount >= 2000) { cash += 2000; this.twoThousandRurCount--; twoThousandRur++; amount-=2000;}
                else if (i == 2 && amount >= 1000) { cash += 1000; this.oneThousandRurCount--; oneThousandRur++; amount-=1000; }
                else if (i == 3 && amount >= 500) { cash += 500; this.fifeHundredRurCount--; fifeHundredRur++; amount-=500; }
                else if (i == 4 && amount >= 200) { cash += 200; this.twoHundredRurCount--; twoHundredRur++; amount-=200; }
                else if (i == 5 && amount >= 100) { cash += 100; this.hundredRurCount--; hundredRur++; amount-=100; }
                else if (i == 6 && amount >= 50) { cash += 50; this.fiftyRurCount--; fiftyRur++; amount-=50; }
                else if (i == 7 && amount >= 10) { cash += 10; this.tenRurCount--; tenRur++; amount-=10; }
                else if (i == 8 && amount >= 5) { cash += 5; this.fiveRurCount--; fiveRur++; amount-=5; }
            }
        }
        if (reqAnAmount != cash) {
            throw new AtmTakeCashException("Не возможно выдать сумму" +
                    ", запрошенная сумма некорректна или в АТМ недостаточно кратных купюр");
        }
        return new Cash.СhooseBanknotes()
                .fiveRurCount(fiveRur)
                .tenRurCount(tenRur)
                .fiftyRurCount(fiftyRur)
                .hundredRurCount(hundredRur)
                .twoHundredRurCount(twoHundredRur)
                .fifeHundredRurCount(fifeHundredRur)
                .oneThousandRurCount(oneThousandRur)
                .twoThousandRurCount(twoThousandRur)
                .fifeThousandRurCount(fifeThousandRur)
                .set();
    }

    @Override
    public boolean giveCash(Cash cash) {
        if (cash.getFiveRurCount() > 0 ||
                cash.getTenRurCount() > 0 ||
                cash.getFiftyRurCount() > 0 ||
                cash.getHundredRurCount() > 0 ||
                cash.getTwoHundredRurCount() > 0 ||
                cash.getFifeHundredRurCount() > 0 ||
                cash.getOneThousandRurCount() > 0 ||
                cash.getTwoThousandRurCount() > 0 ||
                cash.getFifeThousandRurCount() > 0) {
            this.fiveRurCount += cash.getFiveRurCount();
            this.tenRurCount += cash.getTenRurCount();
            this.fiftyRurCount += cash.getFiftyRurCount();
            this.hundredRurCount += cash.getHundredRurCount();
            this.twoHundredRurCount += cash.getTwoHundredRurCount();
            this.fifeHundredRurCount += cash.getFifeHundredRurCount();
            this.oneThousandRurCount += cash.getOneThousandRurCount();
            this.twoThousandRurCount += cash.getTwoThousandRurCount();
            this.fifeThousandRurCount += cash.getFifeThousandRurCount();
            return true;
        } else {
            System.out.println("Вы не вложили купюры для пополнения");
            return false;
        }
    }

    @Override
    public int getBalance() {
        return (this.fiveRurCount * 5) +
                (this.tenRurCount * 10) +
                (this.fiftyRurCount * 50) +
                (this.hundredRurCount * 100) +
                (this.twoHundredRurCount * 200) +
                (this.fifeHundredRurCount * 500) +
                (this.oneThousandRurCount * 1000) +
                (this.twoThousandRurCount * 2000) +
                (this.fifeThousandRurCount * 5000);
    }

    @Override
    public String toString() {
        return "AtmImpl{" +
                "fiveRurCount=" + fiveRurCount +
                ", tenRurCount=" + tenRurCount +
                ", fiftyRurCount=" + fiftyRurCount +
                ", hundredRurCount=" + hundredRurCount +
                ", twoHundredRurCount=" + twoHundredRurCount +
                ", fifeHundredRurCount=" + fifeHundredRurCount +
                ", oneThousandRurCount=" + oneThousandRurCount +
                ", twoThousandRurCount=" + twoThousandRurCount +
                ", fifeThousandRurCount=" + fifeThousandRurCount +
                '}';
    }
}
