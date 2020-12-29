package pl.janeck.carsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janeck.carsapi.model.Car;
import pl.janeck.carsapi.model.Color;
import pl.janeck.carsapi.repository.CarDao;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {


    private final CarDao carDao;

    @Autowired
    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public List<Car> getCars(Color carColor) {
        if (carColor != null) {
            return carDao.findAllByColour(carColor);
        }
        return carDao.getCars();
    }

    public Optional<Car> getCarById(long id) {
        return carDao.findById(id);

    }

    public Optional<Car> removeCar(long id) {
        Optional<Car> carToRemove = carDao.findById(id);
        if (carToRemove.isPresent()) {
            carDao.deleteCar(carToRemove.get().getId());
            return carToRemove;
        }
        return Optional.empty();
    }


    public Optional<Car> addCar(Car carToAdd) {
        carDao.saveCar(carToAdd);
        return Optional.of(carToAdd);

    }


    public Optional<Car> updateCar(Car carToUpdate) {
        Optional<Car> car = carDao.findById(carToUpdate.getId()); // sprawdzamy czy w DB istnieje już obiekt o takim ID
        if (car.isPresent()) {
            carDao.updateCar(carToUpdate);
            return Optional.of(carToUpdate);
        }
        return car;

    }


    public List<Color> colorValues() {
        return Arrays.asList(Color.values());
    }

    public List<Car> getCarByYears(int yearFrom, int yearTo) {
        return carDao.findCarsByYears(yearFrom, yearTo);
    }

}
