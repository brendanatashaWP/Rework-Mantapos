package project.blibli.mantapos.Beans_Model;

import javax.validation.constraints.NotNull;

public class Restaurant {
    @NotNull(message = "restaurant name cannot be empty!")
    private String restaurantName;

    @NotNull(message = "restaurant address cannot be empty!")
    private String restaurantAddress;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

}
