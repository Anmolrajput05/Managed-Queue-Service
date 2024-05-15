package com.anmol.queue.controlller;

import com.anmol.queue.model.Message;
import com.anmol.queue.service.ProducerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/producer")
@Api(value = "Producer Management System", tags = "Producer Management API")
public class ProducerController {
    private final ProducerService producerService;

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/{topicName}")
    @ApiOperation(value = "Produce a message to a topic", response = String.class)
    public ResponseEntity<String> produce(@PathVariable String topicName, @RequestBody Message message) {
        producerService.produce(topicName, message);
        return ResponseEntity.ok("Message produced successfully");
    }
}
