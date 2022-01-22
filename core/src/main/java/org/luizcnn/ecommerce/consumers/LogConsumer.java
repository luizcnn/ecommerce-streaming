package org.luizcnn.ecommerce.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.enums.TopicEnum;
import org.luizcnn.ecommerce.service.impl.KafkaServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogConsumer {

  public static void main(String[] args) {
    final var logConsumer = new LogConsumer();
    try(var kafkaService = new KafkaServiceImpl<>(logConsumer.getTopics(), logConsumer::consume, LogConsumer.class)) {
      kafkaService.run();
    }
  }

  private List<String> getTopics() {
    return Stream.of(TopicEnum.values())
            .map(TopicEnum::getTopic)
            .collect(Collectors.toList());
  }

  private void consume(ConsumerRecord<String, byte[]> record) {
    System.out.println("-------------------------------------");
    System.out.println("LOG: " + record.topic());
    System.out.println(record.key());
    System.out.println(new String(record.value(), StandardCharsets.UTF_8));
    System.out.println(record.partition());
    System.out.println(record.offset());
  }

}
