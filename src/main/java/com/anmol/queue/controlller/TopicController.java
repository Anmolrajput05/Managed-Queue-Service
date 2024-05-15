package com.anmol.queue.controlller;

import com.anmol.queue.model.Topic;
import com.anmol.queue.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/topics")
@Api(value = "Topic Management System", tags = "Topic Management API")
public class TopicController {
    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    @ApiOperation(value = "Create a new topic", response = String.class)
    public ResponseEntity<String> createTopic(@RequestParam String name) {
        topicService.createTopic(name);
        return ResponseEntity.ok("Topic created successfully");
    }

    @GetMapping
    @ApiOperation(value = "List all topics", response = Map.class)
    public ResponseEntity<Map<String, Topic>> listTopics() {
        return ResponseEntity.ok(topicService.listTopics());
    }
}
