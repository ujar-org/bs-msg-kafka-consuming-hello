package dev.knowhowto.kafkaconsuminghello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class KafkaConsumingHelloApplication {
  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(KafkaConsumingHelloApplication.class);
    springApplication.setApplicationStartup(new BufferingApplicationStartup(2048));
    springApplication.run(args);
  }
}
