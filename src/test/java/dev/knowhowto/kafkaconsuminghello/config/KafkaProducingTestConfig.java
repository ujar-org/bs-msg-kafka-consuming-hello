package dev.knowhowto.kafkaconsuminghello.config;

import java.util.HashMap;

import com.iqkv.boot.kafka.config.BaseKafkaProducingConfig;
import dev.knowhowto.kafkaconsuminghello.consumer.dto.GreetingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
class KafkaProducingTestConfig extends BaseKafkaProducingConfig {
  @Bean
  ProducerFactory<String, GreetingDto> greetingMessageProducerFactory(KafkaProperties kafkaProperties, SslBundles sslBundles) {
    return producerFactory(GreetingDto.class, kafkaProperties, sslBundles);
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
