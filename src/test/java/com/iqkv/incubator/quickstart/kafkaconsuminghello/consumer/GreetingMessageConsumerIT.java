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
