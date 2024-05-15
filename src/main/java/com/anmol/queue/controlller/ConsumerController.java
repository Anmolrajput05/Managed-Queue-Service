package com.anmol.queue.controlller;

import com.anmol.queue.service.ConsumerService;
import com.anmol.queue.model.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consumer")
@Api(value = "Consumer Management System", tags = "Consumer Management API")
public class ConsumerController {
    private final ConsumerService consumerService;

    @Autowired
    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @PostMapping("/subscribe/{topicName}")
    @ApiOperation(value = "Subscribe a consumer to a topic", response = String.class)
    public ResponseEntity<String> subscribe(@PathVariable String topicName, @RequestParam String consumerId) {
        consumerService.subscribe(topicName, consumerId);
        return ResponseEntity.ok("Subscribed successfully");
    }

    @GetMapping("/{topicName}")
    @ApiOperation(value = "Consume a message from a topic", response = Message.class)
    public ResponseEntity<Message> consume(@PathVariable String topicName, @RequestParam String consumerId) {
        Message message = consumerService.consume(topicName, consumerId);
        return ResponseEntity.ok(message);
    }
}
