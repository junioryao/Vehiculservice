package com.example.vehiclesapi.vehicles.Exception;

public class CarNotFoundException extends RuntimeException {

  public CarNotFoundException() {
  }

  public CarNotFoundException(String message) {
    super(message);
  }
}
