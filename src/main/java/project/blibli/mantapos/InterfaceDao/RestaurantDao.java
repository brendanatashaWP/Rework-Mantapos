package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.Restaurant;

public interface RestaurantDao {
    void CreateTable();
    int Insert(Restaurant restaurant);
    Restaurant GetRestaurantInfo(String username);
}
