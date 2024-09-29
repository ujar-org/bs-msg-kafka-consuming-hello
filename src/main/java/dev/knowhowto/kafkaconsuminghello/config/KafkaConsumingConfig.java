package dev.knowhowto.kafkaconsuminghello.config;

import com.iqkv.boot.kafka.config.BaseKafkaConsumingConfig;
import dev.knowhowto.kafkaconsuminghello.consumer.dto.GreetingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
class KafkaConsumingConfig extends BaseKafkaConsumingConfig {

  @Autowired
  KafkaConsumingConfig(LocalValidatorFactoryBean validator) {
    super(validator);
  }

  @Bean
  ConsumerFactory<String, GreetingDto> consumeGreetingConsumerFactory(KafkaProperties kafkaProperties, SslBundles sslBundles) {
    return consumerFactory(GreetingDto.class, kafkaProperties, sslBundles);
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, GreetingDto> consumeGreetingKafkaListenerContainerFactory(
      ConsumerFactory<String, GreetingDto> consumeGreetingConsumerFactory,
      @Value("${iqkv.kafka.consumer.threads:2}") int threads,
      DefaultErrorHandler errorHandler) {
    return containerFactory(consumeGreetingConsumerFactory, threads, errorHandler);
  }
}
