package com.example.demo.KafkaProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/kafka")
public class ProducerController
{
    private final Logger logger = Logger.getLogger(ProducerController.class);

    @Value("${spring.kafka.producer.topic}")
    String topic;

    @Autowired
    private ProducerService service;

    @PostMapping("/{city}")
    public String sendMyObject(@PathVariable String city)
    {
        logger.info("/kafka/city GET sendMyObject called.");
        service.sendObject(city);
        logger.info("Exiting sendMyObject.");
        return "City published: " + city;
    }
}