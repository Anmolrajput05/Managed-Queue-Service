package com.anmol.queue.exception;

import com.anmol.queue.Exception.TopicNotFoundException;
import com.anmol.queue.Exception.ConsumerNotFoundException;
import com.anmol.queue.service.TopicService;
import com.anmol.queue.service.ConsumerService;
import com.anmol.queue.service.ProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomExceptionTests {

    @Autowired
    private TopicService topicService;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private ProducerService producerService;

    @Test
    void testTopicNotFoundException() {
        assertThrows(TopicNotFoundException.class, () -> {
            topicService.getTopic("nonexistent-topic");
        });
    }

    @Test
    void testConsumerNotFoundException() {
        assertThrows(ConsumerNotFoundException.class, () -> {
            consumerService.consume("topicname","nonexistent-consumer");
        });
    }


}
