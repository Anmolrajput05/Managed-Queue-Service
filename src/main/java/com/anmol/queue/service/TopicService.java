package com.anmol.queue.service;


import com.anmol.queue.Exception.TopicNotFoundException;
import com.anmol.queue.model.Consumer;
import com.anmol.queue.model.Topic;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TopicService {
    private final Map<String, Topic> topics = new ConcurrentHashMap<>();

    public void createTopic(String name) {
        topics.put(name, new Topic(name));
    }

    public Topic getTopic(String name) {
        Topic topic = topics.get(name);
        if (topic == null) {
            throw new TopicNotFoundException("Topic not found: " + name);
        }
        return topic;
    }

    public void subscribeConsumer(String topicName, Consumer consumer) {
        Topic topic = topics.get(topicName);
        if (topic != null) {
            topic.subscribeConsumer(consumer);
        } else {
            throw new TopicNotFoundException("Topic not found: " + topicName);
        }
    }

    public Map<String, Topic> listTopics() {
        return topics;
    }
}
