package org.da0hn.manageable.redis.topics.publishers;

@FunctionalInterface
public interface Publisher<T> {

  T publish();

}
