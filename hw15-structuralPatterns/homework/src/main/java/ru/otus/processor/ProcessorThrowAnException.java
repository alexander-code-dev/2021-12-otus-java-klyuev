package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorThrowAnException implements Processor{

    private final DateTimeProvider dateTimeProvider;

    public ProcessorThrowAnException(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
            if (dateTimeProvider.getDate().getSecond() % 2 == 0) {
                throw new RuntimeException("throw exception in an even second");
            }
            return message;
    }
}
