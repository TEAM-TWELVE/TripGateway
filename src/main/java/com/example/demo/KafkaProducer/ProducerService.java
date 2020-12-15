package com.example.demo.KafkaProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    @Value("${spring.kafka.producer.topic}")
    String topic;
    private static Logger logger = LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    // Ignore the compiler's warning
    private KafkaTemplate<String, Object> template;

    public void sendObject(String city)
    {
        template.send(topic, city);
        template.flush();
    }
}
