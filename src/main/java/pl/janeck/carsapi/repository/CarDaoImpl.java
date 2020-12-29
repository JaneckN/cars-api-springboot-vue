package pl.janeck.carsapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.janeck.carsapi.model.Car;
import pl.janeck.carsapi.model.Color;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ... comment class...
 *
 * @author JKN janeck@protonmail.com
 * @since 27 December 2020 @ 17:49
 */

@Repository
public class CarDaoImpl implements CarDao {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public CarDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void saveCar(Car newCar) {
        String sql = "INSERT INTO cars(id, mark, model, year, color)VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql, newCar.getId(), newCar.getMark(), newCar.getModel(), newCar.getYear(), newCar.getColor().name());
    }

    @Override
    public List<Car> getCars() {
        String sql = "SELECT * FROM cars";
        List<Car> carList = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return getCarsList(carList, maps);
    }


    @Override
    public Optional<Car> findById(long id) {
        String sql = "SELECT * FROM cars where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new RowMapper<Car>() {
            @Override
            public Car mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Car(resultSet.getLong("id"),
                        resultSet.getString("mark"),
                        resultSet.getString("model"),
                        resultSet.getInt("year"),
                        Color.valueOf(resultSet.getString("color")));
            }
        }, id));
    }

    @Override
    public void updateCar(Car carToUpdate) {
        String sql = "UPDATE cars SET mark=?, model=?, year=?, color=?  WHERE id=?";
        jdbcTemplate.update(sql, carToUpdate.getMark(), carToUpdate.getModel(), carToUpdate.getYear(), carToUpdate.getColor().name(), carToUpdate.getId());
    }


    @Override
    public void deleteCar(long id) {
        String sql = "DELETE FROM cars WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Car> findAllByColour(Color carColor) {
        String sql = "SELECT * FROM cars WHERE color=?";
        List<Car> carList = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, carColor);
        return getCarsList(carList, maps);
    }

    @Override
    public List<Car> findCarsByYears(int yearFrom, int yearTo) {
        String sql = "SELECT * FROM cars WHERE year BETWEEN ? AND ?";
        List<Car> carList = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, yearFrom, yearTo);
        return getCarsList(carList, maps);
    }

    private List<Car> getCarsList(List<Car> carList, List<Map<String, Object>> maps) {
        maps.stream().forEach(element -> carList.add(new Car(
                Long.parseLong(String.valueOf(element.get("id"))),
                String.valueOf(element.get("mark")),
                String.valueOf(element.get("model")),
                Integer.parseInt(String.valueOf(element.get("year"))),
                Color.valueOf(String.valueOf(element.get("color")))
        )));
        return carList;
    }

}
