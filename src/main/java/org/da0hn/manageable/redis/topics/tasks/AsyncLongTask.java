package org.da0hn.manageable.redis.topics.tasks;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@Component
@AllArgsConstructor
public class AsyncLongTask {

  public void execute() {

    log.info("Iniciando execução da tarefa...");
    IntStream.rangeClosed(
      0,
      100
    ).forEach(i -> {
      try {
        log.info("Executando iteração {}", i);
        log.info("Indo dormir...");
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(
          0,
          5
        ));
      }
      catch (final InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
      }
    });
    log.info("Tarefa terminada.");
  }

}
