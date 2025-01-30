/*
 * Copyright 2025 IQKV Team.
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

package com.iqkv.incubator.quickstart.kafkaconsuminghello.producer;

import static com.iqkv.incubator.quickstart.kafkaconsuminghello.config.Constants.TOPIC_DEFINITION_HELLO_WORLD;

import java.util.UUID;

import com.iqkv.boot.kafka.config.KafkaTopicDefinitionProperties;
import com.iqkv.incubator.quickstart.kafkaconsuminghello.consumer.dto.GreetingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class GreetingMessageProducer {
  private final KafkaTemplate<String, GreetingDto> kafkaTemplate;
  private final KafkaTopicDefinitionProperties topicDefinitions;

  /**
   * Send message to Kafka broker with avoiding transaction-aware configuration environment
   */
  public void send(GreetingDto message) {
    final var key = UUID.randomUUID().toString();
    log.info("( {} ) Send message, key: {}, value: {}", Thread.currentThread().getName(), key, message);
    kafkaTemplate.executeInTransaction(op -> op.send(
        topicDefinitions.get(TOPIC_DEFINITION_HELLO_WORLD).getName(),
        key,
        message)
    );
  }
}
