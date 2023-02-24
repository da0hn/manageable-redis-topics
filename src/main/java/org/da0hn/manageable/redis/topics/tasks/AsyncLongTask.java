package org.da0hn.manageable.redis.topics.tasks;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.da0hn.manageable.redis.topics.messages.SimpleMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@AllArgsConstructor
public class AsyncLongTask {

  @Qualifier("jsonRedisTemplate")
  private final RedisTemplate<Object, Object> redisTemplate;

  public void execute(final SimpleMessage data) {
    try {
      log.info("Iniciando execução da tarefa...");

      for (int i = 0; i < 100; i++) {
        log.info(
          "Executando iteração {}",
          i
        );
        this.redisTemplate.opsForValue().set(
          "key-" + data.getId(),
          Progress.of(i)
        );
        log.info("Indo dormir...");
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(
          0,
          5
        ));
      }

      log.info("Tarefa terminada.");
    }
    catch (final Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Value
  public static class Progress {

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime timestamp;

    int progress;

    public static Progress of(final int progress) {
      return new Progress(
        LocalDateTime.now(),
        progress
      );
    }

  }

}
