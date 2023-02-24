package org.da0hn.manageable.redis.topics.listeners.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.da0hn.manageable.redis.topics.config.TaskRegister;
import org.da0hn.manageable.redis.topics.listeners.Listener;
import org.da0hn.manageable.redis.topics.messages.SimpleMessage;
import org.da0hn.manageable.redis.topics.tasks.AsyncLongTask;
import org.springframework.stereotype.Component;

import java.util.concurrent.FutureTask;

@Slf4j
@Component
@AllArgsConstructor
public class LongTaskTopicListener implements Listener<SimpleMessage> {

  private final AsyncLongTask asyncLongTask;

  private final TaskRegister taskRegister;

  @Override
  public void listen(final SimpleMessage data) {
    log.info(
      "Mensagem recebida {}. Iniciando task...",
      data
    );
    final FutureTask<Void> task = new FutureTask<>(() -> {
      this.asyncLongTask.execute(data);
      return null;
    });
    this.taskRegister.put(
      data.getId().toString(),
      task
    );
    task.run();
  }

}
