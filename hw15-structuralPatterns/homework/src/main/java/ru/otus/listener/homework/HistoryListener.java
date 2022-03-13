package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> message = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        this.message.put(msg.getId(), new Message.Builder(msg.getId()).message(msg).build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(this.message.get(id));
    }
}
