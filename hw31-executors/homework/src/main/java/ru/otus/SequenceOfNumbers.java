package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SequenceOfNumbers {
    private static final Logger logger = LoggerFactory.getLogger(SequenceOfNumbers.class);
    private final List<Integer> listForThreadOne = List.of(1,2,3,4,5,6,7,8,9,10,9,8,7,6,5,4,3,2,1);
    private final List<Integer> listForThreadTwo = List.of(1,2,3,4,5,6,7,8,9,10,9,8,7,6,5,4,3,2,1);
    private volatile String lastName = "Thread-1";

    private synchronized void printElement(Integer element)  {
        try {
            while (lastName.equals(Thread.currentThread().getName())) {
                this.wait();
            }
            logger.info("{}:{} ", Thread.currentThread().getName(), element);
            lastName = Thread.currentThread().getName();
            sleep();
            this.notifyAll();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        SequenceOfNumbers sequenceOfNumbers = new SequenceOfNumbers();

        new Thread(() -> {
            sequenceOfNumbers.listForThreadOne.forEach(sequenceOfNumbers::printElement);
        }).start();

        new Thread(() -> {
            sequenceOfNumbers.listForThreadTwo.forEach(sequenceOfNumbers::printElement);
        }).start();
    }

    private static void sleep() {
        try {
            Thread.sleep((long) (Math.random()*1000));
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
