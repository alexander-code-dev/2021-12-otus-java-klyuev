package ru.otus;

import com.google.common.collect.Lists;

import java.util.List;

public class HelloOtus {

    private static String hello = "Hello";
    private static String otus = "Otus";
    private static String delight = "!!!";

    public static void main(String... args) {
        List<String> listStr = Lists.newArrayList(hello, otus, delight);
        listStr.stream().forEach(System.out::print);
    }
}
