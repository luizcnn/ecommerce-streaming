package org.luizcnn.ecommerce.kafkaproperties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import static java.util.Objects.isNull;

public final class ConsumerProperties {

  private static final String SERVER_ADDRESS = "127.0.0.1:9092";

  private ConsumerProperties() {}

  public static Properties getProperties(Map<String, String> customProperties) {
    final var properties = getDefaultProperties();
    properties.putAll(customProperties);
    return properties;
  }

  public static <T> Properties getProperties(Class<T> clazz, Map<String, String> customProperties) {
    final var properties = getProperties(clazz);
    properties.putAll(customProperties);

    return properties;
  }

  public static <T> Properties getProperties(Class<T> clazz) {
    final var properties = getDefaultProperties();
    if(isNull(clazz)) {
      return properties;
    }
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, clazz.getSimpleName());
    properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, clazz.getSimpleName().concat("_").concat(UUID.randomUUID().toString()));

    return properties;
  }

  private static Properties getDefaultProperties() {
    final var properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER_ADDRESS);
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
    properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
    properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

    return properties;
  }
}
