package com.anmol.queue.service;

import com.anmol.queue.Exception.ConsumerNotFoundException;
import com.anmol.queue.Exception.TopicNotFoundException;
import com.anmol.queue.model.Message;
import com.anmol.queue.model.Topic;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ConsumerServiceTest {

    @Mock
    private TopicService topicService;

    @InjectMocks
    private ConsumerService consumerService;

    private final String testTopicName = "testTopic";
    private final String testConsumerId = "testConsumer";





    @Test
    public void testConsumeMessageFromNonExistingTopic() {
        // Mock the behavior of the TopicService to return null (indicating topic not found)
        Mockito.when(topicService.getTopic(testTopicName)).thenReturn(null);

        // Call the consume method and expect a TopicNotFoundException
        assertThrows(TopicNotFoundException.class, () -> consumerService.consume(testTopicName, testConsumerId));
    }

    @Test
    public void testConsumeMessageFromExistingTopicButNotSubscribedConsumer() {
        // Create a mock Topic with a message but no subscribed consumer
        Topic mockTopic = new Topic(testTopicName);
        mockTopic.addMessage(new Message());

        // Mock the behavior of the TopicService to return the mock Topic
        Mockito.when(topicService.getTopic(testTopicName)).thenReturn(mockTopic);

        // Call the consume method and expect a ConsumerNotFoundException
        assertThrows(ConsumerNotFoundException.class, () -> consumerService.consume(testTopicName, testConsumerId));
    }


}
