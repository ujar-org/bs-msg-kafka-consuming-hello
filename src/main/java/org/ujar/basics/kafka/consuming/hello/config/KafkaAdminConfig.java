package org.ujar.basics.kafka.consuming.hello.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.ujar.boot.starter.kafka.KafkaSectionProperties;

@Configuration
@RequiredArgsConstructor
class KafkaAdminConfig {
  private final KafkaSectionProperties properties;

  @Bean
  NewTopic helloWorldKafkaTopic() {
    var definition = properties.getTopics().get("hello-world");
    return TopicBuilder
        .name(definition.name())
        .partitions(definition.partitions())
        .config(TopicConfig.RETENTION_MS_CONFIG, "" + definition.retention().toMillis())
        .build();
  }
}
