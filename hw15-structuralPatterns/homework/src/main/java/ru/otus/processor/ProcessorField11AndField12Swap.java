package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorField11AndField12Swap implements Processor {
    @Override
    public Message process(Message message) {
        String field11 = message.getField12();
        String field12 = message.getField11();
        return message.toBuilder()
                .field11(field11)
                .field12(field12)
                .build();
    }
}
