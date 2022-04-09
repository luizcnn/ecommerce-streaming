package org.luizcnn.ecommerce.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.luizcnn.ecommerce.utils.exceptions.BytesToObjectException;
import org.luizcnn.ecommerce.utils.exceptions.WriteValueToBytesException;

import java.io.IOException;

public final class JsonUtils {

  private JsonUtils() {}

  public static byte[] writeValueAsBytes(Object o) {
    try {
      return JsonUtils.getInstance().writeValueAsBytes(o);
    } catch (JsonProcessingException e) {
      throw new WriteValueToBytesException("Cannot process object to bytes", e);
    }
  }

  public static <T> T readValue(byte[] bytes, Class<T> clazz) {
    try {
      return JsonUtils.getInstance().readValue(bytes, clazz);
    } catch (IOException e) {
      throw new BytesToObjectException("Cannot deserialize bytes into " + clazz.getName());
    }
  }

  private static ObjectMapper getInstance() {
    return new ObjectMapper();
  }

}
