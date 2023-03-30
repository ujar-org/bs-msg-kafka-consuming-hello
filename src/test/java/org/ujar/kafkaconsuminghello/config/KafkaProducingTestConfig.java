package org.ujar.kafkaconsuminghello.config;

import java.util.HashMap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.ujar.boot.kafka.config.BaseKafkaProducingConfig;
import org.ujar.kafkaconsuminghello.consumer.dto.GreetingDto;

@Configuration
@RequiredArgsConstructor
class KafkaProducingTestConfig extends BaseKafkaProducingConfig {
  @Bean
  ProducerFactory<String, GreetingDto> greetingMessageProducerFactory(KafkaProperties kafkaProperties) {
    return producerFactory(GreetingDto.class, kafkaProperties);
  }

  @Bean
  KafkaTemplate<String, GreetingDto> greetingMessageKafkaTemplate(
      ProducerFactory<String, GreetingDto> greetingMessageProducerFactory) {
    return kafkaTemplate(greetingMessageProducerFactory);
  }

  @Bean
  KafkaOperations<Object, Object> errorHandlingKafkaTemplate() {
    return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(new HashMap<>()));
  }
}
