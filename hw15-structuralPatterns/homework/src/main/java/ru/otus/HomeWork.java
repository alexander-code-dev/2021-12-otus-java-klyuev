package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.Processor;
import ru.otus.processor.ProcessorField11AndField12Swap;
import ru.otus.processor.ProcessorThrowAnException;

import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяьться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */

        List<Processor> processors = List.of(new LoggerProcessor(new ProcessorField11AndField12Swap()),
                new LoggerProcessor(new ProcessorThrowAnException()));

        ComplexProcessor complexProcessor = new ComplexProcessor(processors, ex -> {});

        HistoryListener historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        ObjectForMessage objectForMessage = new ObjectForMessage();
        List<String> strForObjectForMessage = new ArrayList<>();
        strForObjectForMessage.add("field13");
        objectForMessage.setData(strForObjectForMessage);

        long id = 2L;
        Message message = new Message.Builder(id)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field13(objectForMessage)
                .build();

        Message result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(historyListener);
    }
}
