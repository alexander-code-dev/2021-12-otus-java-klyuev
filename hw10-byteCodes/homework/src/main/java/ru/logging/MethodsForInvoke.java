package ru.logging;

public class MethodsForInvoke {

    private final String name;
    private final int countArg;

    public MethodsForInvoke(String name, int countArg) {
        this.name = name;
        this.countArg = countArg;
    }

    public String getName() {
        return name;
    }

    public int getCountArg() {
        return countArg;
    }
}
