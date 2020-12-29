package pl.janeck.carsapi.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.janeck.carsapi.model.Car;
import pl.janeck.carsapi.model.Color;
import pl.janeck.carsapi.service.CarService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    public CarController() {
    }

    @GetMapping
    @ApiOperation(value = "Get all cars", notes = "When you use this method without any parameter, you get all cars. ")
    public ResponseEntity<List<Car>> getAllCars(@RequestParam(required = false) Color color) {
        return new ResponseEntity<>(carService.getCars(color), HttpStatus.OK);
    }

    @GetMapping(value = "/getColors")
    public ResponseEntity<List<Color>> getColors(){
        return new ResponseEntity<>(carService.colorValues(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Get Car by ID", notes = "Get Car by ID")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> car = carService.getCarById(id);
        if (car.isPresent()) {
            return new ResponseEntity<>(car.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Add Car", notes = "Add car")
    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Optional<Car> carToAdd = carService.addCar(car);
        if (carToAdd.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);


    }

    @ApiOperation(value = "Remove Car", notes = "Remove Car")
    @DeleteMapping("/{id}")
    public ResponseEntity<Car> removeCar(@PathVariable long id) {
        Optional<Car> car = carService.removeCar(id);
        if (!car.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(car.get(), HttpStatus.OK);

    }

    @ApiOperation(value = "Update Car", notes = "Update Car")
    @PutMapping
    public ResponseEntity<Car> updateCar(@RequestBody Car car) {
        Optional<Car> carToUpdate = carService.updateCar(car);
        if (carToUpdate.isPresent()) {
            return new ResponseEntity<>(carToUpdate.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/years")
    @ApiOperation(value = "Get cars by Years", notes = "")
    public ResponseEntity<List<Car>> getCarsByYears(@RequestParam int yearFrom, @RequestParam int yearTo) {
        return new ResponseEntity<>(carService.getCarByYears(yearFrom, yearTo), HttpStatus.OK);
    }


}
