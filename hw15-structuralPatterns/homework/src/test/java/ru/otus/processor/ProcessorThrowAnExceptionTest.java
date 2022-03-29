package ru.otus.processor;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProcessorThrowAnExceptionTest {

    @DisplayName("Тестируем вызовы процессора c выпадением исключения в четную секунду")
    @Test
    void process() {
        LocalDateTime localDateTime = LocalDateTime
                .ofEpochSecond(2,0, ZoneOffset.ofTotalSeconds(0));
        Message messageIn = new Message.Builder(11L)
                .field11("field11")
                .field12("field12")
                .build();

        Processor processor = new ProcessorThrowAnException(() -> localDateTime);

        Throwable exception = assertThrows(RuntimeException.class, () -> processor.process(messageIn));
        AssertionsForClassTypes.assertThat(exception.getMessage()).isEqualTo("throw exception in an even second");
    }
}