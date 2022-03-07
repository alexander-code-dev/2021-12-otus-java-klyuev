package ru.solid.Impl;

import ru.solid.GetMoney;

public class Cash implements GetMoney {

    private final int fiveRurCount;
    private final int tenRurCount;
    private final int fiftyRurCount;
    private final int hundredRurCount;
    private final int twoHundredRurCount;
    private final int fifeHundredRurCount;
    private final int oneThousandRurCount;
    private final int twoThousandRurCount;
    private final int fifeThousandRurCount;

    public static class СhooseBanknotes {
        private int fiveRurCount = 0;
        private int tenRurCount = 0;
        private int fiftyRurCount = 0;
        private int hundredRurCount = 0;
        private int twoHundredRurCount = 0;
        private int fifeHundredRurCount = 0;
        private int oneThousandRurCount = 0;
        private int twoThousandRurCount = 0;
        private int fifeThousandRurCount = 0;

        public СhooseBanknotes fiveRurCount(int fiveRurCount) { this.fiveRurCount = fiveRurCount; return this;}
        public СhooseBanknotes tenRurCount(int tenRurCount) { this.tenRurCount = tenRurCount; return this;}
        public СhooseBanknotes fiftyRurCount(int fiftyRurCount) { this.fiftyRurCount = fiftyRurCount; return this;}
        public СhooseBanknotes hundredRurCount(int hundredRurCount) { this.hundredRurCount = hundredRurCount; return this;}
        public СhooseBanknotes twoHundredRurCount(int twoHundredRurCount) { this.twoHundredRurCount = twoHundredRurCount; return this;}
        public СhooseBanknotes fifeHundredRurCount(int fifeHundredRurCount) { this.fifeHundredRurCount = fifeHundredRurCount; return this;}
        public СhooseBanknotes oneThousandRurCount(int oneThousandRurCount) { this.oneThousandRurCount = oneThousandRurCount; return this;}
        public СhooseBanknotes twoThousandRurCount(int twoThousandRurCount) { this.twoThousandRurCount = twoThousandRurCount; return this;}
        public СhooseBanknotes fifeThousandRurCount(int fifeThousandRurCount) { this.fifeThousandRurCount = fifeThousandRurCount; return this;}

        public Cash set() {
            return new Cash(this);
        }
    }

    private Cash(СhooseBanknotes chooseBanknotes) {
        this.fiveRurCount = chooseBanknotes.fiveRurCount;
        this.tenRurCount = chooseBanknotes.tenRurCount;
        this.fiftyRurCount = chooseBanknotes.fiftyRurCount;
        this.hundredRurCount = chooseBanknotes.hundredRurCount;
        this.twoHundredRurCount = chooseBanknotes.twoHundredRurCount;
        this.fifeHundredRurCount = chooseBanknotes.fifeHundredRurCount;
        this.oneThousandRurCount = chooseBanknotes.oneThousandRurCount;
        this.twoThousandRurCount = chooseBanknotes.twoThousandRurCount;
        this.fifeThousandRurCount = chooseBanknotes.fifeThousandRurCount;
    }

    @Override
    public int getFiveRurCount() {
        return fiveRurCount;
    }

    @Override
    public int getTenRurCount() {
        return tenRurCount;
    }

    @Override
    public int getFiftyRurCount() {
        return fiftyRurCount;
    }

    @Override
    public int getHundredRurCount() {
        return hundredRurCount;
    }

    @Override
    public int getTwoHundredRurCount() {
        return twoHundredRurCount;
    }

    @Override
    public int getFifeHundredRurCount() {
        return fifeHundredRurCount;
    }

    @Override
    public int getOneThousandRurCount() {
        return oneThousandRurCount;
    }

    @Override
    public int getTwoThousandRurCount() {
        return twoThousandRurCount;
    }

    @Override
    public int getFifeThousandRurCount() {
        return fifeThousandRurCount;
    }

    @Override
    public int getSum() {
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
        return "Cash{" +
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
