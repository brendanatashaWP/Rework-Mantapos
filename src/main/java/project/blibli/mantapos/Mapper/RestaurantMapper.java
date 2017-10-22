package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Beans_Model.Restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantMapper implements RowMapper<Restaurant> {
    @Override
    public Restaurant mapRow(ResultSet resultSet, int i) throws SQLException {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(resultSet.getString("restaurant_name"));
        restaurant.setRestaurantAddress(resultSet.getString("restaurant_address"));
        return restaurant;
    }
}
