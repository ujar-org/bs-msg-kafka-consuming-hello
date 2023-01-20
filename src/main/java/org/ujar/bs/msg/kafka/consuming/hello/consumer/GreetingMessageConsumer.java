package org.ujar.bs.msg.kafka.consuming.hello.consumer;

import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.ujar.boot.starter.kafka.exception.ConsumerRecordProcessingException;
import org.ujar.bs.msg.kafka.consuming.hello.consumer.dto.GreetingDto;

@Component
@RequiredArgsConstructor
@Slf4j
@Getter
class GreetingMessageConsumer {

  private CountDownLatch latch = new CountDownLatch(1);
  private String payload;

  @KafkaListener(containerFactory = "consumeGreetingKafkaListenerContainerFactory",
                 topics = "${ujar.kafka.topics.hello-world.name}",
                 groupId = "${spring.kafka.consumer.group-id}")
  public void consume(ConsumerRecord<String, GreetingDto> consumerRecord) {
    try {
      log.info("( {} ) Received message, key: {}, value: {}",
          Thread.currentThread().getName(), consumerRecord.key(), consumerRecord.value());
      payload = consumerRecord.value().toString();
      latch.countDown();
    } catch (Exception e) {
      throw new ConsumerRecordProcessingException("Error processing message data.", e);
    }
  }

  public void resetLatch() {
    latch = new CountDownLatch(1);
  }
}
