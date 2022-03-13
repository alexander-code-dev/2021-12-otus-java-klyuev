package ru.otus.processor;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProcessorThrowAnExceptionTest {

    @DisplayName("Тестируем вызовы процессора c выпадением исключения в четную секунду")
    @RepeatedTest(5)
    @Test
    void process() throws InterruptedException {

        Message messageIn = new Message.Builder(11L)
                .field11("field11")
                .field12("field12")
                .build();

        Processor processor = new ProcessorThrowAnException();

        Throwable exception = assertThrows(RuntimeException.class,
                () -> processor.process(messageIn));
        LocalDateTime localDateTime = LocalDateTime.parse(exception.getMessage());
        AssertionsForClassTypes.assertThat(localDateTime.getSecond() % 2).isEqualTo(0);
        TimeUnit.SECONDS.sleep(1);
    }
}