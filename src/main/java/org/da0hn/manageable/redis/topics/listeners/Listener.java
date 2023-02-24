package org.da0hn.manageable.redis.topics.listeners;

@FunctionalInterface
public interface Listener<T> {

  void listen(T data);

}
