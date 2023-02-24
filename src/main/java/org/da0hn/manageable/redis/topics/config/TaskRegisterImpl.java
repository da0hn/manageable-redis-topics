package org.da0hn.manageable.redis.topics.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

@Slf4j
@Component
@ApplicationScope
public class TaskRegisterImpl implements TaskRegister {

  private static final Map<String, FutureTask<?>> tasks = new ConcurrentHashMap<>();

  @Override
  public void put(
    final String label,
    final FutureTask<?> task
  ) {
    log.info(
      "Registrando task {}",
      label
    );
    tasks.put(
      label,
      task
    );
  }

  @Override
  public void removeTask(final String label) {
    log.info(
      "Removendo task {}",
      label
    );
    final FutureTask<?> future = tasks.get(label);
    future.cancel(true);
    log.info(
      "Task {} removida",
      label
    );
  }

  @Override
  public List<String> getKeys() {
    return new ArrayList<>(tasks.keySet());
  }

  @Override
  public Set<Map.Entry<String, FutureTask<?>>> entrySet() {
    return tasks.entrySet();
  }

}
