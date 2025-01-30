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

package com.iqkv.incubator.quickstart.kafkaconsuminghello.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.TimeUnit;

import com.iqkv.incubator.quickstart.kafkaconsuminghello.consumer.dto.GreetingDto;
import com.iqkv.incubator.quickstart.kafkaconsuminghello.producer.GreetingMessageProducer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class GreetingMessageConsumerIT {

  private final GreetingMessageProducer producer;
  private final GreetingMessageConsumer consumer;

  @Autowired
  public GreetingMessageConsumerIT(GreetingMessageProducer producer, GreetingMessageConsumer consumer) {
    this.producer = producer;
    this.consumer = consumer;
  }

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void shouldConsumeMessage() throws InterruptedException {
    final var greeting = new GreetingDto("Hello, World!");
    producer.send(greeting);

    boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
    assertThat(messageConsumed).isTrue();
    assertThat(consumer.getPayload(), containsString(greeting.toString()));
    consumer.resetLatch();
  }
}
