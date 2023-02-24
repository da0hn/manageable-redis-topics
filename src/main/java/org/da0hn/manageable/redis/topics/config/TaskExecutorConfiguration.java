package org.da0hn.manageable.redis.topics.config;

import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

@Configuration
public class TaskExecutorConfiguration {

  @Bean
  public Executor taskExecutor() {
    final var coreCount = Runtime.getRuntime().availableProcessors();
    return new TaskExecutorBuilder()
      .corePoolSize(coreCount)
      .maxPoolSize(coreCount)
      .queueCapacity(5)
      .threadNamePrefix("Topic-")
      .build();
  }

}
