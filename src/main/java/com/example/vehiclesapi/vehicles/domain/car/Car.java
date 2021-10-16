package com.example.vehiclesapi.vehicles.domain.car;

import com.example.vehiclesapi.vehicles.domain.Location;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

/** Declares the Car class, related variables and methods. */
@Entity
public class Car {

  @Id @GeneratedValue private Long id;

  @CreatedDate private LocalDateTime createdAt;

  @LastModifiedDate private LocalDateTime modifiedAt;

  @Valid
  @Enumerated(EnumType.STRING)
  @Embedded
  private Condition condition;

  @Valid @Embedded private Details details = new Details();

  @Valid @Embedded private Location location = new Location(0d, 0d);

  @Transient private String price;

  @Version private int version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(LocalDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  public Condition getCondition() {
    return condition;
  }

  public void setCondition(Condition condition) {
    this.condition = condition;
  }

  public Details getDetails() {
    return details;
  }

  public void setDetails(Details details) {
    this.details = details;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Car car = (Car) o;
    return version == car.version && Objects.equals(id, car.id) && Objects.equals(createdAt, car.createdAt) && Objects.equals(modifiedAt, car.modifiedAt) && condition == car.condition && Objects.equals(details, car.details) && Objects.equals(location, car.location) && Objects.equals(price, car.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, modifiedAt, condition, details, location, price, version);
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }
}
