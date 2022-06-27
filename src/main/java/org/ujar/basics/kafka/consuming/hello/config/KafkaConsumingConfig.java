package org.ujar.basics.kafka.consuming.hello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.ujar.basics.kafka.consuming.hello.consumer.dto.GreetingDto;
import org.ujar.boot.starter.kafka.config.BaseKafkaConsumingConfig;

@Configuration
class KafkaConsumingConfig extends BaseKafkaConsumingConfig {

  @Autowired
  public KafkaConsumingConfig(LocalValidatorFactoryBean validator) {
    super(validator);
  }

  @Bean
  ConsumerFactory<String, GreetingDto> consumeGreetingConsumerFactory(KafkaProperties kafkaProperties) {
    return consumerFactory(GreetingDto.class, kafkaProperties);
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, GreetingDto> consumeGreetingKafkaListenerContainerFactory(
      ConsumerFactory<String, GreetingDto> consumeGreetingConsumerFactory,
      @Value("${ujar.kafka.consumer.threads:2}") int threads,
      DefaultErrorHandler errorHandler) {
    return containerFactory(consumeGreetingConsumerFactory, threads, errorHandler);
  }
}
