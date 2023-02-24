package org.da0hn.manageable.redis.topics.response;

import lombok.Value;

import java.util.Map;
import java.util.concurrent.FutureTask;

@Value
public class TaskData {

  String uuid;

  boolean isCancelled;

  boolean isDone;

  public static TaskData of(final Map.Entry<String, ? extends FutureTask<?>> entry) {
    return new TaskData(
      entry.getKey(),
      entry.getValue().isCancelled(),
      entry.getValue().isDone()
    );
  }

}
