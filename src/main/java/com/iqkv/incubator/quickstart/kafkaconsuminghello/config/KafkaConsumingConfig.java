/*
 * Copyright 2024 IQKV Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iqkv.incubator.quickstart.kafkaconsuminghello.config;

import com.iqkv.boot.kafka.config.BaseKafkaConsumingConfig;
import com.iqkv.incubator.quickstart.kafkaconsuminghello.consumer.dto.GreetingDto;
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
