package com.example.vehiclesapi.vehicles.HypermediaHateoas;

import com.example.vehiclesapi.vehicles.domain.car.Car;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/*
* ResourceSupport is now RepresentationModel

Resource is now EntityModel

Resources is now CollectionModel

PagedResources is now PagedModel
*
* */
public class CarRepresentationModel extends RepresentationModelAssemblerSupport<Car, CarResources> {

  public CarRepresentationModel(Class<?> controllerClass, Class<CarResources> resourceType) {
    super(controllerClass, resourceType);
  }

  /*@Override
  public CarResources toModel(Car entity) {
    return super.createModelWithId(entity.getId(), entity);
  }*/

  // customizing model
  // TODO customizing a ref model
  @Override
  public CarResources toModel(Car entity) {
    CarResources carResources = new CarResources(entity);

    carResources.add(
        linkTo(this.getControllerClass()).slash(entity.getId()).slash("good").withSelfRel());
    carResources.add(linkTo(this.getControllerClass()).slash(43).slash("bill").withSelfRel());
    return carResources;
  }

  public List<CarResources> toModels(List<Car> entities) {
    List<CarResources> carResourcesCollection = new ArrayList<>();
    entities.forEach(
        car -> {
          carResourcesCollection.add(toModel(car));
        });
    return carResourcesCollection;
  }

  @Override
  protected CarResources instantiateModel(Car entity) {
    return new CarResources(entity);
  }
}
