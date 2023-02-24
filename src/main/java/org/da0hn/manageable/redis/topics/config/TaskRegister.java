package org.da0hn.manageable.redis.topics.config;

import java.util.List;
import java.util.concurrent.FutureTask;

public interface TaskRegister {

  void put(
    String label,
    FutureTask<?> task
  );

  void removeTask(String label);

  List<String> getKeys();

}
