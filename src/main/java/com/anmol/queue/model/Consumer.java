package com.anmol.queue.model;

import lombok.Data;

import java.util.concurrent.ConcurrentLinkedQueue;

@Data
public class Consumer {
    private String id;
    private String topicName;
    private ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<>();

    public Consumer(String id, String topicName) {
        this.id = id;
        this.topicName = topicName;
    }

    public void addMessage(Message message) {
        messageQueue.offer(message);
    }

    public Message getNextMessage() {
        return messageQueue.poll();
    }
}
