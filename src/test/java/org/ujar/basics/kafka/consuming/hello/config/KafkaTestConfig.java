package org.ujar.basics.kafka.consuming.hello.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.ujar.basics.kafka.consuming.hello.consumer.dto.GreetingDto;

@Configuration
@RequiredArgsConstructor
public class KafkaTestConfig {
  @Bean
  ProducerFactory<String, GreetingDto> greetingMessageProducerFactory(KafkaProperties kafkaProperties) {
    try (var serde = new JsonSerde<>(GreetingDto.class, new ObjectMapper())) {
      var consumerProperties = kafkaProperties.getProducer().buildProperties();
      var consumerFactory = new DefaultKafkaProducerFactory<>(consumerProperties,
          new StringSerializer(),
          serde.serializer());
      consumerFactory.setTransactionIdPrefix(getTransactionPrefix());
      return consumerFactory;
    }
  }

  @Bean
  KafkaTemplate<String, GreetingDto> greetingMessageKafkaTemplate(
      ProducerFactory<String, GreetingDto> greetingMessageProducerFactory) {
    return new KafkaTemplate<>(greetingMessageProducerFactory);
  }

  @Bean
  KafkaOperations<Object, Object> errorHandlingKafkaTemplate() {
    return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(new HashMap<>()));
  }

  private String getTransactionPrefix() {
    return "tx-" + UUID.randomUUID() + "-";
  }
}
