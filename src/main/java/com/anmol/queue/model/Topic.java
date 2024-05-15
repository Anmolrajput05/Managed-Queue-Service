package com.anmol.queue.model;

import com.anmol.queue.model.Consumer;
import com.anmol.queue.model.Message;
import lombok.Data;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Data
public class Topic {
    private String name;
    private Queue<Message> messages = new ConcurrentLinkedQueue<>();
    private ConcurrentHashMap<String, Consumer> consumers = new ConcurrentHashMap<>();

    public Topic(String name) {
        this.name = name;
    }

    public void addMessage(Message message) {
        messages.offer(message);
        consumers.values().forEach(consumer -> consumer.addMessage(message));
    }

    public Message getNextMessageForConsumer(String consumerId) {
        Consumer consumer = consumers.get(consumerId);
        return consumer != null ? consumer.getNextMessage() : null;
    }

    public void subscribeConsumer(Consumer consumer) {
        consumers.put(consumer.getId(), consumer);
        messages.forEach(consumer::addMessage);
    }

    public boolean isConsumerSubscribed(String consumerId) {
        return consumers.containsKey(consumerId);
    }
}
