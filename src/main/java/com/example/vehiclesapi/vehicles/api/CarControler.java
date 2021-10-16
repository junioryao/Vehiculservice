package com.example.vehiclesapi.vehicles.api;

import com.example.vehiclesapi.vehicles.HypermediaHateoas.CarRepresentationModel;
import com.example.vehiclesapi.vehicles.HypermediaHateoas.CarResources;
import com.example.vehiclesapi.vehicles.amqp.SendMessageToBroker;
import com.example.vehiclesapi.vehicles.domain.car.Car;
import com.example.vehiclesapi.vehicles.service.CarService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
;

/** Implements a REST-based controller for the Vehicles API. */
@RestController
@Api(value = "Car controller ")
@RequestMapping("/cars")
class CarControler {

  @Autowired private CarService carService;
  //@Autowired private SendMessageToBroker sendMessageToBroker;
  List<Car> carSaverCollector = new ArrayList<>();
  // private final CarResourceAssembler assembler;

  /**
   * Creates a list to store any vehicles.
   *
   * @return list of vehicles
   */
  @GetMapping
  ResponseEntity<?> list() {

    List<Car> cars = carService.list();
    // CollectionModel<EntityModel<Car>>  carResources =  CollectionModel.wrap(cars) ;

    // grouping them up a better way
    /*cars.forEach(car -> carResources.add(WebMvcLinkBuilder.linkTo(CarControler.class)
    .slash(car.getId())
    .withRel("getOneCar")));*/

    // adding one by one
    /* carResources.add(WebMvcLinkBuilder.linkTo(CarControler.class)
    .slash("id")
    .withRel("getOneCar")); */
    // sendMessageToBroker.SendMessage(cars.get(0));
    //return new CarRepresentationModel(CarControler.class, CarResources.class).toModels(cars);
    return new ResponseEntity<>(
        new SimpleMessage("Car registered successfully", HttpStatus.ACCEPTED.value()),
        HttpStatus.OK);
  }


  @GetMapping("/{id}")
  Optional<Car> get(@PathVariable Long id) {
    return carService.findById(id);
  }

  @PostMapping
  ResponseEntity<?> post(@Valid @RequestBody Car car) throws Exception {


    carService.save(car);
    return new ResponseEntity<>(
        new SimpleMessage("Car registered successfully", HttpStatus.ACCEPTED.value()),
        HttpStatus.ACCEPTED);
  }

  @PutMapping("/{id}")
  ResponseEntity<?> put(@PathVariable Long id, @Valid @RequestBody Car car) throws Exception {
    car.setId(id);
    carService.save(car);
    return new ResponseEntity<>(
        new SimpleMessage("Car updated sucessfully", HttpStatus.ACCEPTED.value()),
        HttpStatus.ACCEPTED);
  }

  /**
   * Removes a vehicle from the system.
   *
   * @param id The ID number of the vehicle to remove.
   * @return response that the related vehicle is no longer in the system
   */
  @DeleteMapping("/{id}")
  ResponseEntity<?> delete(@PathVariable Long id) {
    carService.delete(id);
    return new ResponseEntity<>(
        new SimpleMessage("Car removed", HttpStatus.ACCEPTED.value()), HttpStatus.ACCEPTED);
  }
}
