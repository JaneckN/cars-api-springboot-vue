package pl.janeck.carsapi.repository;

import pl.janeck.carsapi.model.Car;
import pl.janeck.carsapi.model.Color;

import java.util.List;
import java.util.Optional;

/**
 * ... comment class...
 *
 * @author JKN janeck@protonmail.com
 * @since 27 December 2020 @ 17:47
 */


public interface CarDao {

    void saveCar(Car newCar);

    List<Car> getCars();

    Optional<Car> findById(long id);

    void updateCar(Car carToUpdate);

    void deleteCar(long id);

    List<Car> findAllByColour(Color carColor);

    List<Car> findCarsByYears(int yearFrom, int yearTo);

}
