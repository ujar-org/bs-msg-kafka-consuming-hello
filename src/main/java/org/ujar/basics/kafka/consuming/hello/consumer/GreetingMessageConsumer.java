package org.ujar.basics.kafka.consuming.hello.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.ujar.basics.kafka.consuming.hello.consumer.dto.GreetingDto;
import org.ujar.boot.starter.kafka.exception.ConsumerRecordProcessingException;

@Component
@RequiredArgsConstructor
@Slf4j
class GreetingMessageConsumer {

  @KafkaListener(containerFactory = "consumeGreetingKafkaListenerContainerFactory",
                 topics = "${ujar.kafka.topics.hello-world.name}",
                 groupId = "${spring.kafka.consumer.group-id}")
  public void consume(ConsumerRecord<String, GreetingDto> consumerRecord) {
    try {
      log.info("( {} ) Received message, key: {}, value: {}",
          Thread.currentThread().getName(), consumerRecord.key(), consumerRecord.value());
    } catch (Exception e) {
      throw new ConsumerRecordProcessingException("Error processing message data.", e);
    }
  }
}
