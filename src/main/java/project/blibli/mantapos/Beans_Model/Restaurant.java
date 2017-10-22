package project.blibli.mantapos.Beans_Model;

import javax.validation.constraints.NotNull;

public class Restaurant {
    private String id_restaurant;

    public String getId_restaurant() {
        return id_restaurant;
    }

    public void setId_restaurant(String id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    @NotNull(message = "restaurant name cannot be empty!")
    private String restaurant_name;

    @NotNull(message = "restaurant address cannot be empty!")
    private String restaurant_address;

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_address() {
        return restaurant_address;
    }

    public void setRestaurant_address(String restaurant_address) {
        this.restaurant_address = restaurant_address;
    }

}
