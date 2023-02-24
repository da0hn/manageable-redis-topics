package org.da0hn.manageable.redis.topics.publishers.impl;

import lombok.extern.slf4j.Slf4j;
import org.da0hn.manageable.redis.topics.messages.SimpleMessage;
import org.da0hn.manageable.redis.topics.publishers.Publisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateLongTaskPublisher implements Publisher<SimpleMessage> {

  private final RedisTemplate<Object, Object> redisTemplate;

  @Value("${app.manageable.redis.topics}")
  private String topic;

  public CreateLongTaskPublisher(@Qualifier("jsonRedisTemplate") final RedisTemplate<Object, Object> redisTemplate) {this.redisTemplate = redisTemplate;}

  @Override
  public SimpleMessage publish() {
    final var message = SimpleMessage.newInstance("Executar task longa! from: " + Thread.currentThread().getName());
    log.info("Enviando mensagem {} para o tópico {}", message, this.topic);
    this.redisTemplate.convertAndSend(
      this.topic,
      message
    );
    return message;
  }

}
