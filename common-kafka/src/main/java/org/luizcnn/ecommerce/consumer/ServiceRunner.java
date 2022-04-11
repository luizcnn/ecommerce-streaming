package org.luizcnn.ecommerce.consumer;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ServiceRunner {

  private final Callable<Void> provider;

  public ServiceRunner(ConsumerFactory factory) {
    this.provider = new ServiceProvider(factory);
  }

  public void start(Integer threadCount) {
    final var pool = Executors.newFixedThreadPool(threadCount);
    for(int i = 0; i <= threadCount; i++) {
      pool.submit(provider);
    }
  }
}
