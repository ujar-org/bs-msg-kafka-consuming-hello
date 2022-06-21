package org.ujar.basics.kafka.consuming.hello.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.ujar.basics.kafka.consuming.hello.consumer.dto.GreetingDto;

@Configuration
class KafkaConfig {

  @Bean
  ConsumerFactory<String, GreetingDto> consumeGreetingConsumerFactory(KafkaProperties kafkaProperties) {
    var consumerProperties = kafkaProperties.getConsumer().buildProperties();
    try (var serde = new JsonSerde<>(GreetingDto.class, new ObjectMapper())) {
      return new DefaultKafkaConsumerFactory<>(consumerProperties,
          new ErrorHandlingDeserializer<>(new StringDeserializer()), new ErrorHandlingDeserializer<>(
          serde.deserializer()));
    }
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, GreetingDto> consumeGreetingKafkaListenerContainerFactory(
      ConsumerFactory<String, GreetingDto> consumeGreetingConsumerFactory,
      @Value("${ujar.kafka.consumer.threads:2}") int threads) {
    ConcurrentKafkaListenerContainerFactory<String, GreetingDto> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumeGreetingConsumerFactory);
    factory.setConcurrency(threads);
    return factory;
  }
}
