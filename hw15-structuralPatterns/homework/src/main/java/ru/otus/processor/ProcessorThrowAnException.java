package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class ProcessorThrowAnException implements Processor{
    @Override
    public Message process(Message message) {
        while(true) {
            if (LocalDateTime.now().getSecond() % 2 == 0) {
                throw new RuntimeException(String.valueOf(LocalDateTime.now()));
            }
        }
    }
}
