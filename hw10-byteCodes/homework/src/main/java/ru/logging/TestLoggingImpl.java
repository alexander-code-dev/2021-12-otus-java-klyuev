package ru.logging;

public class TestLoggingImpl implements TestLogging {

    //@Log
    @Override
    public void calculation(int param1) {
        //System.out.println("Print in method calculation, param1: " + param1);
    }
    //@Log
    @Override
    public void calculation(int param1, int param2) {
        //System.out.println("Print in method calculation, param1: " + param1 + ", param2: " + param2);
    }
    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        //System.out.println("Print in method calculation, param1: " + param1 + ", param2: " + param2 + ", param3: " + param3);
    }
}
