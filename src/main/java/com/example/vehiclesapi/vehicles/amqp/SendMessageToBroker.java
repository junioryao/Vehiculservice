package com.example.vehiclesapi.vehicles.amqp;


import com.example.vehiclesapi.vehicles.domain.car.Car;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessageToBroker  {
  @Autowired
  RabbitTemplate rabbitTemplate ;


  public void SendMessage(Car car ){
    rabbitTemplate.convertAndSend("test","testkey",car);
    System.out.println("message sent to mq "+car);
  }



}
