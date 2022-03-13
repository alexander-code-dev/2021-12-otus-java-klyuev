package ru.otus.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.Assertions.assertThat;

class ProcessorField11AndField12SwapTest {

    @Test
    @DisplayName("Тестируем вызовы процессора по подмене field11 и field12")
    void process() {

        Message messageIn = new Message.Builder(1L)
                .field11("field11")
                .field12("field12")
                .build();

        Processor processor = new ProcessorField11AndField12Swap();
        Message messageOut = processor.process(messageIn);

        assertThat(messageIn.getField11()).isEqualTo(messageOut.getField12());
        assertThat(messageIn.getField12()).isEqualTo(messageOut.getField11());
    }
}