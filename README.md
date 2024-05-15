Design Document for Managed Queue Service
Table of Contents
Introduction
Objectives
Architecture
Components
Data Model
API Design
Swagger Integration
Thread Safety
Persistent Data
Error Handling
Security Considerations
Deployment
1. Introduction
The Managed Queue Service is a Spring Boot application that provides a managed queue service supporting various topics. Producers can add data to these topics, and consumers can retrieve data from them. The service ensures thread safety, persistent data, and dynamic topic subscription.
2. Objectives
Provide a reliable and scalable managed queue service.
Ensure data persistence and thread safety.
Support dynamic topic subscription.
Expose RESTful APIs for producers and consumers.
Integrate Swagger for API documentation.
3. Architecture
The architecture of the Managed Queue Service is based on a microservice design using Spring Boot. It includes the following components:
Controllers: Handle HTTP requests and responses.
Services: Contain business logic and manage data.
Models: Represent the data structures.
Configuration: Configure application settings and Swagger.
High-Level Architecture
diff
Copy code
+--------------------------+
| Controllers |
| (Producer, Consumer, |
| Topic) |
+-----------+--------------+
 |
+-----------v--------------+
| Services |
| (ProducerService, |
| ConsumerService, |
| TopicService) |
+-----------+--------------+
 |
+-----------v--------------+
| Models |
| (Topic, Message, |
| Consumer) |
+--------------------------+

4. Components
4.1 Controllers
ProducerController: Handles endpoints for producers to add messages to topics.
ConsumerController: Manages endpoints for consumers to subscribe and retrieve messages from topics.
TopicController: Provides endpoints for creating and listing topics.
4.2 Services
ProducerService: Contains logic for adding messages to topics.
ConsumerService: Manages consumer subscriptions and message retrieval.
TopicService: Handles creation and listing of topics.
4.3 Models
Topic: Represents a queue topic.
Message: Represents a message in a topic.
Consumer: Represents a consumer subscribed to a topic.
4.4 Configuration
SwaggerConfig: Configures Swagger for API documentation.
5. Data Model
5.1 Topic
java
Copy code
public class Topic {
 private String name;
 private BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
 private Map<String, Integer> consumerOffsets = new ConcurrentHashMap<>();
}

5.2 Message
java
Copy code
public class Message {
 private String content;
}

5.3 Consumer
java
Copy code
public class Consumer {
 private String id;
 private String topicName;
}

6. API Design
6.1 Create a Topic
Endpoint: POST /api/topics
Parameters: name (query parameter)
Response: 200 OK
Description: Creates a new topic.
6.2 List All Topics
Endpoint: GET /api/topics
Response: 200 OK (JSON object containing topics)
Description: Lists all available topics.
6.3 Produce a Message to a Topic
Endpoint: POST /api/producer/{topicName}
Parameters: topicName (path variable)
Body: JSON object representing the message
Response: 200 OK
Description: Adds a message to the specified topic.
6.4 Subscribe to a Topic
Endpoint: POST /api/consumer/subscribe/{topicName}
Parameters: topicName (path variable), consumerId (query parameter)
Response: 200 OK
Description: Subscribes a consumer to the specified topic.
6.5 Consume a Message from a Topic
Endpoint: GET /api/consumer/{topicName}
Parameters: topicName (path variable), consumerId (query parameter)
Response: 200 OK (JSON object representing the message)
Description: Retrieves the next message from the specified topic for the given consumer.
7. Swagger Integration
Configuration
The Swagger configuration is defined in SwaggerConfig.java:
java
Copy code
package com.example.queue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
 @Bean
 public Docket api() {
 return new Docket(DocumentationType.SWAGGER_2)
 .select()
 .apis(RequestHandlerSelectors.basePackage("com.example.queue.controller"))
 .paths(PathSelectors.any())
 .build();
 }
}

Accessing Swagger UI
After starting the application, Swagger UI can be accessed at:
bash
Copy code
http://localhost:8080/swagger-ui/

8. Thread Safety
BlockingQueue is used to ensure thread-safe access to messages.
ConcurrentHashMap is used for managing consumer offsets in a thread-safe manner.
9. Persistent Data
Currently, the data is stored in memory. For persistence, consider integrating with a database like MySQL or MongoDB.
10. Error Handling
Basic error handling is implemented. Each controller method returns appropriate HTTP status codes. Future enhancements can include more detailed error messages and logging.
11. Security Considerations
Security is not implemented in this version. Future enhancements could include:
Authentication and authorization mechanisms.
Role-based access control.
Secure communication with HTTPS.
12. Deployment
Prerequisites
Java 8 or newer
Maven
Steps to Build and Run
Clone the repository.
Navigate to the project root directory.
Build the project using Maven:
sh
Copy code
mvn clean install


Run the application:
sh
Copy code
mvn spring-boot:run


Docker Deployment
To containerize the application, add a Dockerfile:
dockerfile
Copy code
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/managed-queue-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

Build and run the Docker image:
sh
Copy code
docker build -t managed-queue-service .
docker run -p 8080:8080 managed-queue-service

Conclusion
This document provides an overview of the design and implementation of the Managed Queue Service project. It includes the architecture, components, API design, Swagger integration, thread safety, and deployment instructions. Future enhancements can focus on data persistence, security, and scalability.
