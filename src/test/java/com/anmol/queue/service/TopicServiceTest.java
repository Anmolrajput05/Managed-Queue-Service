package com.anmol.queue.service;

import com.anmol.queue.Exception.TopicNotFoundException;
import com.anmol.queue.model.Consumer;
import com.anmol.queue.model.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TopicServiceTest {

    private TopicService topicService;

    @BeforeEach
    void setUp() {
        topicService = new TopicService();
    }

    @Test
    void testCreateTopic() {
        topicService.createTopic("test-topic");
        Topic topic = topicService.getTopic("test-topic");
        assertNotNull(topic);
        assertEquals("test-topic", topic.getName());
    }

    @Test
    void testGetTopic() {
        topicService.createTopic("test-topic");
        Topic topic = topicService.getTopic("test-topic");
        assertNotNull(topic);
        assertEquals("test-topic", topic.getName());
    }

    @Test
    void testGetTopicNotFound() {
        assertThrows(TopicNotFoundException.class, () -> {
            topicService.getTopic("nonexistent-topic");
        });
    }

    @Test
    void testSubscribeConsumer() {
        topicService.createTopic("test-topic");
        Consumer consumer = new Consumer("consumer1","Ad");
        topicService.subscribeConsumer("test-topic", consumer);
        Topic topic = topicService.getTopic("test-topic");
        assertTrue(topic.getConsumers().contains(consumer));
    }

    @Test
    void testSubscribeConsumerTopicNotFound() {
        Consumer consumer = new Consumer("consumer1","fs");
        assertThrows(TopicNotFoundException.class, () -> {
            topicService.subscribeConsumer("nonexistent-topic", consumer);
        });
    }

    @Test
    void testListTopics() {
        topicService.createTopic("topic1");
        topicService.createTopic("topic2");
        Map<String, Topic> topics = topicService.listTopics();
        assertEquals(2, topics.size());
        assertTrue(topics.containsKey("topic1"));
        assertTrue(topics.containsKey("topic2"));
    }
}
