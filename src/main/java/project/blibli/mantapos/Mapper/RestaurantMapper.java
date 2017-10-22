package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Beans_Model.Restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantMapper implements RowMapper<Restaurant> {
    @Override
    public Restaurant mapRow(ResultSet resultSet, int i) throws SQLException {
        Restaurant restaurant = new Restaurant();
        restaurant.setId_restaurant(resultSet.getInt("id"));
        restaurant.setRestaurant_name(resultSet.getString("restaurant_name"));
        restaurant.setRestaurant_address(resultSet.getString("restaurant_address"));
        return restaurant;
    }
}
