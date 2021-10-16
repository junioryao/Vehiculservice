package com.example.vehiclesapi.vehicles.api;

public class SimpleMessage {

  public String message ;
  public int values  ;


  public SimpleMessage() {

  }
  public SimpleMessage(String message, int values) {
    this.message = message;
    this.values = values;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getValues() {
    return values;
  }

  public void setValues(int values) {
    this.values = values;
  }
}
