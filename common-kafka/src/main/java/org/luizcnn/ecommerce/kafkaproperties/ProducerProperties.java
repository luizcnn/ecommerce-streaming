package org.luizcnn.ecommerce.kafkaproperties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;

public final class ProducerProperties {

  private static final String SERVER_ADDRESS = "127.0.0.1:9092";
  private static final String ACKS_CONFIG_VALUE  = "all";

  private ProducerProperties() {}

  public static Properties getProperties(Map<String, String> customProperties) {
    final var properties = getDefaultProperties();
    properties.putAll(customProperties);

    return properties;
  }

  public static Properties getDefaultProperties() {
    final var properties = new Properties();
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER_ADDRESS);
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
    properties.setProperty(ProducerConfig.ACKS_CONFIG, ACKS_CONFIG_VALUE);

    return properties;
  }

}
