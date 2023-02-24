package org.da0hn.manageable.redis.topics.listeners.impl;

import lombok.extern.slf4j.Slf4j;
import org.da0hn.manageable.redis.topics.listeners.Listener;
import org.da0hn.manageable.redis.topics.messages.SimpleMessage;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LongTaskTopicListener implements Listener<SimpleMessage> {

  @Override
  public void listen(final SimpleMessage data) {
    log.info(
      "Mensagem recebida {}. Iniciando task...",
      data
    );
  }

}
