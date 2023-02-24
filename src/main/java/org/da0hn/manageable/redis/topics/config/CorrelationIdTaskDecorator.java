package org.da0hn.manageable.redis.topics.config;

import org.springframework.core.task.TaskDecorator;

public class CorrelationIdTaskDecorator /*implements TaskDecorator*/ {

  //  @Override
  //  public Runnable decorate(final Runnable task) {
  //    final String correlationId = UUID.randomUUID().toString();
  //    return () -> {
  //      if (!StringUtils.isEmpty(correlationId)) {
  //        ThreadLocal.withInitial()correlationIdThreadLocal.put(correlationId);
  //      }
  //      try {
  //        task.run();
  //      }
  //      finally {
  //        correlationIdThreadLocal.remove();
  //      }
  //
  //    };
  //  }

}
