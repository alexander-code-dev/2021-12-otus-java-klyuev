package ru.otus.annotations.usage;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class AnnotationWorkExample {

    private int num1;
    private int num2;

    public AnnotationWorkExample() {}

    @Before
    public void beforeFirst() {
        num1 = 300;
        System.out.println("Before first annotation hashCode: "
                + Integer.toHexString(hashCode()) + ", set num1: "+num1);
    }
    @Before
    public void beforeSecond() {
        num2 = 600;
        System.out.println("Before Second annotation hashCode: "
                + Integer.toHexString(hashCode()) + ", set num2: "+num2);
    }
    @Test
    public void testFirst() throws Exception {
        int sum = num1 + num2;
        System.out.println("First test annotation hashCode: "
                + Integer.toHexString(hashCode()) + ", num1 + num2: " + sum);
        //throw new Exception("No work");
    }
    @Test
    public void testSecond() {
        int multiplication = num1 * num2;
        System.out.println("Second test annotation hashCode: "
                + Integer.toHexString(hashCode()) + ", num1 * num2: " + multiplication);
    }
    @After
    public void afterFirst() {
        num1 = 3;
        System.out.println("After First annotation hashCode: "
                + Integer.toHexString(hashCode()) + ", set num1: "+num1);
    }
    @After
    public void afterSecond() {
        num2 = 6;
        System.out.println("After Second annotation hashCode: "
                + Integer.toHexString(hashCode()) + ", set num2: "+num2);
    }
}
