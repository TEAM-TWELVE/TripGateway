package com.example.demo.KafkaProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class ProducerController
{
    @Value("${spring.kafka.producer.topic}")
    String topic;

    @Autowired
    private ProducerService service;

    @PostMapping("/{city}")
    public String sendMyObject(@PathVariable String city)
    {
        service.sendObject(city);
        return "City published: " + city;
    }
}