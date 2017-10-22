package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Beans_Model.Restaurant;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.RestaurantDao;
import project.blibli.mantapos.Mapper.RestaurantMapper;

public class RestaurantDaoImpl implements RestaurantDao{
    private static final String table_name = "restaurant";
    private static final String id = "id";
    private static final String restaurant_name = "restaurant_name";
    private static final String restaurant_address = "restaurant_address";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public RestaurantDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(" +
                id + " SERIAL PRIMARY KEY, " +
                restaurant_name + " TEXT NOT NULL, " +
                restaurant_address + " TEXT NOT NULL)";
        jdbcTemplate.execute(query);
    }

    @Override
    public int Insert(Restaurant restaurant) {
        int status;
        String query = "INSERT INTO " + table_name +
                "(" +
                restaurant_name + "," +
                restaurant_address +
                ")" +
                " VALUES (?,?)";
        status = jdbcTemplate.update(query, new Object[]{
                restaurant.getRestaurantName(), restaurant.getRestaurantAddress()
        });
        return status;
    }

    @Override
    public Restaurant GetRestaurantInfo(String username) {
        String query = "SELECT users.id_restaurant," +
                "restaurant.id," +
                "restaurant.restaurant_name," +
                "restaurant.restaurant_address" +
                " FROM users, restaurant WHERE users.id_restaurant=restaurant.id AND users.username=?";
        Restaurant restaurant = jdbcTemplate.queryForObject(query, new Object[] {username}, new RestaurantMapper());
        return restaurant;
    }
}
