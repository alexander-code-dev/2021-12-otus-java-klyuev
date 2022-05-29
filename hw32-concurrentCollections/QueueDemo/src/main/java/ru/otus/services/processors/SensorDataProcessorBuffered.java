package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;

// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final SensorDataBufferedWriter writer;
    private final ArrayBlockingQueue<SensorData> queue;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.writer = writer;
        this.queue = new ArrayBlockingQueue<>(bufferSize);
    }

    @Override
    public synchronized void process(SensorData data) {
        Thread.yield();
        if (!queue.offer(data)) {
            flush();
            queue.add(data);
        }
    }

    public synchronized void flush() {
        try {
            if (!queue.isEmpty()) {
                writer.writeBufferedData(queue.stream()
                        .sorted(Comparator.comparing(SensorData::getMeasurementTime))
                        .toList());
                queue.clear();
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
