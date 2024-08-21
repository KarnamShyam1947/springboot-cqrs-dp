package com.shyam.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${application.kafka.topic-name}")
    private String topicName;

    @Bean
    NewTopic newTopic() {
        return new NewTopic(topicName, 5, (short) 1);
    }
    
}
