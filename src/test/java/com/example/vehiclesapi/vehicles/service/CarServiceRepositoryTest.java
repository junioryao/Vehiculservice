package com.example.vehiclesapi.vehicles.service;

import com.example.vehiclesapi.vehicles.domain.car.Car;
import com.example.vehiclesapi.vehicles.domain.car.CarRepository;
import com.example.vehiclesapi.vehicles.domain.car.Condition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CarServiceRepositoryTest {

  private static String jsonPut;
  @Autowired CarRepository repository;
  Car car;

  @BeforeAll
  static void beforeAll() {
    jsonPut =
        "{\n"
            + "  \"condition\": \"NEW\",\n"
            + "  \"details\": {\n"
            + "    \"body\": \"sedan\",\n"
            + "    \"model\": \"sedan\",\n"
            + "    \"manufacturer\": {\n"
            + "      \"name\": \"RangeOver\"\n"
            + "    },\n"
            + "    \"numberOfDoors\": 4,\n"
            + "    \"fuelType\": \"petrol\",\n"
            + "    \"engine\": \"400 h\",\n"
            + "    \"mileage\": 324,\n"
            + "    \"modelYear\": 2016,\n"
            + "    \"productionYear\": 2017,\n"
            + "    \"externalColor\": \"red\"\n"
            + "  },\n"
            + "  \"location\": {\n"
            + "    \"lat\": 123.334,\n"
            + "    \"lon\": 1256.875,\n"
            + "    \"address\": \" 8 rue rene hamon\",\n"
            + "    \"city\": \"Paris\",\n"
            + "    \"State\": \"ile de france\",\n"
            + "    \"zip\": \"94400\"\n"
            + "  }\n"
            + "}";
  }

  private Car getCar() {
    Car car = new Gson().fromJson(jsonPut, Car.class);
    repository.save(car);
    return car;
  }

  @Test
  @DisplayName("Save Car test ")
  void Save() throws JsonProcessingException {

    Car car = getCar();
    Assertions.assertThat(car.getId()).isGreaterThan(0);
  }

  @Test
  void findById() {
    Car car = getCar();
    Assertions.assertThat(car.getId()).isEqualTo(repository.findById(car.getId()).get().getId());
  }

  @Test
  void list() {}

  @Test
  void UpdateObject() throws Exception {
    Car car = getCar();
    car.setCondition(Condition.USED);

    Assertions.assertThat(repository.findById(car.getId()).get().getCondition())
        .isEqualTo(Condition.USED);
  }

  @Test
  void delete() throws Exception {

    Car car = getCar();
    Long savedID = (car.getId());
    System.out.println(savedID);
    // car.setPrice(null);
    // car.setCondition(null);
    // car.setLocation(null);
    // repository.deleteById(car.getId());
    // org.junit.jupiter.api.Assertions.assertThrows(CarNotFoundException.class , ()->
    // repository.findById(savedID));
  }
}
