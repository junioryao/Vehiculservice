package com.example.vehiclesapi.vehicles.HypermediaHateoas;

import com.example.vehiclesapi.vehicles.domain.Location;
import com.example.vehiclesapi.vehicles.domain.car.Car;
import com.example.vehiclesapi.vehicles.domain.car.Condition;
import com.example.vehiclesapi.vehicles.domain.car.Details;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

/*
 * use a resources to convert every tacos object , into a new tacos resources object
 * this class is a copy of car model
 * */
@Relation(collectionRelation = "Car")
public class CarResources extends RepresentationModel<CarResources> {
  private long  id ;
  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

  private Condition condition;

  private Details details = new Details();

  private Location location = new Location(0d, 0d);

  private String price;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public CarResources(Car car) {
    this.id = car.getId();
    this.condition = car.getCondition();
    this.createdAt = car.getCreatedAt();

    this.modifiedAt = car.getModifiedAt();

    this.details = car.getDetails();

    this.location = car.getLocation();

    this.price = car.getPrice();
  }

  public CarResources() {
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }

  public Condition getCondition() {
    return condition;
  }

  public Details getDetails() {
    return details;
  }

  public Location getLocation() {
    return location;
  }

  public String getPrice() {
    return price;
  }
}
