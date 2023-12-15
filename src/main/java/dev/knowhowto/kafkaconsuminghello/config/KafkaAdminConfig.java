package dev.knowhowto.kafkaconsuminghello.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.iqkv.boot.kafka.config.KafkaErrorHandlingProperties;
import org.iqkv.boot.kafka.config.KafkaTopicDefinitionProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "iqkv.kafka.admin.create-topics", havingValue = "true")
class KafkaAdminConfig {
  private final KafkaTopicDefinitionProperties topicDefinitions;

  private final KafkaErrorHandlingProperties errorHandlingProperties;


  @Bean
  NewTopic helloWorldKafkaTopic() {
    final var definition = topicDefinitions.get(Constants.TOPIC_DEFINITION_HELLO_WORLD);
    return TopicBuilder
        .name(definition.getName())
        .partitions(definition.getPartitions())
        .config(TopicConfig.RETENTION_MS_CONFIG, "" + definition.getRetention().toMillis())
        .build();
  }

  @Bean
  NewTopic helloWorldKafkaDeadLetterTopic() {
    return TopicBuilder
        .name(topicDefinitions.get(
            Constants.TOPIC_DEFINITION_HELLO_WORLD).getName()
              + errorHandlingProperties.getDeadLetter().getSuffix())
        .partitions(1)
        .config(TopicConfig.RETENTION_MS_CONFIG,
            "" + errorHandlingProperties.getDeadLetter().getRetention().toMillis())
        .build();
  }
}
