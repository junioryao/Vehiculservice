package com.example.vehiclesapi.vehicles.service;

import com.example.vehiclesapi.vehicles.Exception.CarNotFoundException;
import com.example.vehiclesapi.vehicles.client.maps.MapsClient;
import com.example.vehiclesapi.vehicles.client.prices.PriceClient;
import com.example.vehiclesapi.vehicles.domain.car.Car;
import com.example.vehiclesapi.vehicles.domain.car.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

/**
 * Implements the car service create, read, update or delete information about vehicles, as well as
 * gather related location and price data when desired.
 */
@Service
@Slf4j
public class CarService {
  @Autowired private final CarRepository repository;
  @Autowired private PriceClient priceClient;
  @Autowired private MapsClient mapsClient;

  @Autowired private ExecutorService executorService;

  public CarService(CarRepository repository) {

    this.repository = repository;
  }

  /**
   * Gathers a list of all vehicles
   *
   * @return a list of all vehicles in the CarRepository
   */
  public List<Car> list() {
    return repository.findAll();
  }

  /**
   * Gets car information by ID (or throws exception if non-existent)
   *
   * @param id the ID number of the car to gather information on
   * @return the requested car's information, including location and price
   */
  public Optional<Car> findById(Long id) {

    Optional<Car> car = repository.findById(id);

    if (car.isEmpty()) throw new CarNotFoundException("car not found");

    car.get().setPrice(String.valueOf(priceClient.getPrice(id).getPrice()));

    car.get().setLocation(mapsClient.getAddress(car.get().getLocation()));

    return car;
  }

  /**
   * Either creates or updates a vehicle, based on prior existence of car
   *
   * @param car A car object, which can be either new or existing
   * @return the new/updated car is stored in the repository
   */
  public Object save(Car car) throws Exception {
    ;
    try {
      log.info(Thread.currentThread().getName());
      Thread.sleep(2000);
      if (car.getId() != null) {
        Optional<Car> mycar =
            Optional.ofNullable(
                repository
                    .findById(car.getId())
                    .orElseThrow(() -> new CarNotFoundException("Car not found")));


        if(mycar.isPresent() ){

              mycar.get().setDetails(car.getDetails());
              mycar.get().setLocation(car.getLocation());
              mycar.get().setModifiedAt(LocalDateTime.now());
              return ( repository.save(mycar.get()));
        }

      }
      car.setCreatedAt(LocalDateTime.now());
      return repository.save(car);

    } catch (OptimisticLockingFailureException e) {
      throw new CarNotFoundException(e.getMessage());
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * Deletes a given car by ID
   *
   * @param id the ID number of the car to delete
   * @return
   */
  @Transactional
  public void delete(Long id) {
    try {
      Optional<Car> car = repository.findById(id);
      if (car.isPresent()) {
        // need  to delete the related relation object then remove the object
        repository.deleteById(id);
      } else {
        throw new CarNotFoundException("Car not found");
      }
    } catch (OptimisticLockException e) {
      throw new OptimisticLockException(e.getMessage());
    }
  }
}
