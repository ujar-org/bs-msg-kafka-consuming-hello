/*
 * Copyright 2024 IQKV.
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

import java.util.HashMap;

import com.iqkv.boot.kafka.config.BaseKafkaProducingConfig;
import com.iqkv.incubator.quickstart.kafkaconsuminghello.consumer.dto.GreetingDto;
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
