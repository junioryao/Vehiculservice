package com.example.vehiclesapi.vehicles.api;

import com.example.vehiclesapi.vehicles.amqp.SendMessageToBroker;
import com.example.vehiclesapi.vehicles.domain.car.Car;
import com.example.vehiclesapi.vehicles.domain.car.CarRepository;
import com.example.vehiclesapi.vehicles.service.CarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(CarControler.class)
class CarControlerTest {
  @Autowired MockMvc mockMvc;
  @MockBean CarRepository repository;
  @MockBean CarService carService;

  @Test
  void list() throws Exception {
    Car car = new Car();
    Mockito.when(carService.list()).thenReturn(List.of(car));
    mockMvc
        .perform(MockMvcRequestBuilders.get("/cars/")).andDo(print())
        .andExpect(MockMvcResultMatchers.status().is(200));
    Mockito.verify(carService, times(1)).list();
  }

  @Test
  void list1() throws Exception {
    Car car = new Car();
    Mockito.when(carService.list()).thenReturn(List.of(car));
   // Mockito.doNothing().when(sendMessageToBroker).SendMessage(isA(Car.class));
    mockMvc
        .perform(MockMvcRequestBuilders.get("/cars"))
        .andExpect(MockMvcResultMatchers.status().is(200))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    Mockito.verify(carService, times(1)).list();
    //verify(sendMessageToBroker, times(1)).SendMessage(isA(Car.class));
  }

  @Test
  void get() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/cars/{id}", 3))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName(" test when for post failure ")
  void post() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post("/cars/"))
        .andExpect(MockMvcResultMatchers.status().is(400));
  }

  @Test
  @DisplayName(" test when for post succesfully  ")
  void postSucess() throws Exception {

    // language=JSON
    String myjson =
        "{\n"
            + "  \"condition\": \"NEW\",\n"
            + "  \"details\": {\n"
            + "    \"body\": \"sedan\",\n"
            + "    \"model\": \"sedan\",\n"
            + "    \"manufacturer\": {\n"
            + "      \"name\": \"RangeOver\"\n"
            + "    },\n"
            + "    \"numberOfDoors\": 2,\n"
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

    String json2 =
        "{\n"
            + "  \"message\" : \"Car registered successfully\",\n"
            + "    \"values\" : 202"
            + "  \n"
            + "}";
    Car car = new Car();
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/cars/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(myjson))
        .andExpect(MockMvcResultMatchers.status().is(202))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(json2));
  }

  @Test
  void put() throws Exception {
    String jsonPut =
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

    String JsonResponse =
        "{\n" + "  \"message\": \"Car updated sucessfully\",\n" + "  \"values\": 202\n" + "}";

    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/cars/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPut))
        .andExpect(MockMvcResultMatchers.status().is(202))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().json(JsonResponse));
  }

  @Test
  void delete() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/cars/2"))
        .andExpect(MockMvcResultMatchers.status().is(202));
  }
}
