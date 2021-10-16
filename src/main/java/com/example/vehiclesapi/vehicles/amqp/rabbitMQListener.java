package com.example.vehiclesapi.vehicles.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Cache.Channel;
import org.springframework.stereotype.Service;

@Service
public class rabbitMQListener implements MessageListener {


  @Override
  @RabbitListener(queues = "test")
  public void onMessage(Message message) {
    System.out.println("Consuming Message - " + new String(message.getBody()));
  }
}
