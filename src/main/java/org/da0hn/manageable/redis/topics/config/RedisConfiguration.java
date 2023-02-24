package org.da0hn.manageable.redis.topics.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.da0hn.manageable.redis.topics.listeners.Listener;
import org.da0hn.manageable.redis.topics.messages.SimpleMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

  private final ObjectMapper objectMapper;

  private final String redisManageableTopic;

  public RedisConfiguration(
    final ObjectMapper objectMapper,
    @Value("${app.manageable.redis.topics}") final String redisManageableTopic
  ) {
    this.objectMapper = objectMapper;
    this.redisManageableTopic = redisManageableTopic;
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory(final RedisProperties props) {
    final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    config.setHostName(props.getHost());
    config.setPort(props.getPort());
    config.setPassword(props.getPassword());
    return new LettuceConnectionFactory(config);
  }

  @Bean
  @Qualifier("jsonRedisTemplate")
  public RedisTemplate<Object, Object> redisTemplate(final RedisConnectionFactory redisConnectionFactory) {
    final RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setDefaultSerializer(this.jackson2JsonRedisSerializer(SimpleMessage.class));
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(this.jackson2JsonRedisSerializer(SimpleMessage.class));
    template.setValueSerializer(this.jackson2JsonRedisSerializer(SimpleMessage.class));
    return template;
  }

  @Bean
  public RedisMessageListenerContainer manageableTopic(
    final RedisConnectionFactory connectionFactory,
    @Qualifier("longTaskTopicListenerAdapter") final MessageListenerAdapter listenerAdapter
  ) {
    final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.addMessageListener(
      listenerAdapter,
      new PatternTopic(this.redisManageableTopic)
    );
    return container;
  }

  @Bean
  public MessageListenerAdapter longTaskTopicListenerAdapter(
    @Qualifier("longTaskTopicListener") final Listener<SimpleMessage> listener
  ) {
    final var adapter = new MessageListenerAdapter(
      listener,
      "listen"
    );
    adapter.setSerializer(this.jackson2JsonRedisSerializer(SimpleMessage.class));
    return adapter;
  }

  private <T> Jackson2JsonRedisSerializer<T> jackson2JsonRedisSerializer(final Class<T> clazz) {
    final var jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
      clazz
    );
    jackson2JsonRedisSerializer.setObjectMapper(this.objectMapper);
    return jackson2JsonRedisSerializer;
  }

}
