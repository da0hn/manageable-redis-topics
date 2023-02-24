package org.da0hn.manageable.redis.topics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.DependsOn;

@SpringBootApplication
@DependsOn("redisConnectionFactory")
public class ManageableRedisTopicsApplication {

  public static void main(final String[] args) {
    SpringApplication.run(
      ManageableRedisTopicsApplication.class,
      args
    );
  }

}
