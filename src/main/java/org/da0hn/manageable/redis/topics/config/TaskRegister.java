package org.da0hn.manageable.redis.topics.config;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.FutureTask;

public interface TaskRegister {

  void put(
    String label,
    FutureTask<?> task
  );

  void removeTask(String label);

  List<String> getKeys();

  Set<Map.Entry<String, FutureTask<?>>> entrySet();

}
