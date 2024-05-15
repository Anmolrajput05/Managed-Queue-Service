package com.anmol.queue.service;

import com.anmol.queue.Exception.TopicNotFoundException;
import com.anmol.queue.model.Message;
import com.anmol.queue.model.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProducerServiceTest {

    @Mock
    private TopicService topicService;

    @InjectMocks
    private ProducerService producerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProduce() {
        String topicName = "test-topic";
        Message message = new Message();
        message.setContent("message");
        Topic topic = new Topic(topicName);

        when(topicService.getTopic(topicName)).thenReturn(topic);

    }

    @Test
    void testProduceTopicNotFound() {
        String topicName = "nonexistent-topic";
        Message message = new Message();

        when(topicService.getTopic(topicName)).thenThrow(new TopicNotFoundException("Topic not found"));

        assertThrows(TopicNotFoundException.class, () -> {
            producerService.produce(topicName, message);
        });


    }
}
