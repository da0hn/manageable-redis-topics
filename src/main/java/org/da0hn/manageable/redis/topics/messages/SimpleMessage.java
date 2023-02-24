package org.da0hn.manageable.redis.topics.messages;

import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class SimpleMessage {

  @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  LocalDateTime time;

  UUID id;

  String message;

  public static SimpleMessage newInstance(final String message) {
    return new SimpleMessage(
      LocalDateTime.now(),
      UUID.randomUUID(),
      message
    );
  }

}
