package com.example.vehiclesapi.vehicles.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {


  @Bean
  Queue queue() {
    return new Queue("test", true);
  }

  @Bean
  DirectExchange exchange() {
    return new DirectExchange("test");
  }

  @Bean
  Binding binding(Queue queue, DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with("testkey");
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }


  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }

  @Bean
  MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
    SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
    simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
    simpleMessageListenerContainer.setQueues(queue());
    simpleMessageListenerContainer.setMessageListener(new rabbitMQListener());
    simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
    return simpleMessageListenerContainer;

  }


}



