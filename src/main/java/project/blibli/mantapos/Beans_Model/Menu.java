package project.blibli.mantapos.Beans_Model;

public class Menu {
    private int id_menu;
    private String name_menu, photo_location_menu, category_menu;
    private int price_total_menu;

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }

    public String getName_menu() {
        return name_menu;
    }

    public void setName_menu(String name_menu) {
        this.name_menu = name_menu;
    }

    public String getPhoto_location_menu() {
        return photo_location_menu;
    }

    public void setPhoto_location_menu(String photo_location_menu) {
        this.photo_location_menu = photo_location_menu;
    }

    public int getPrice_total_menu() {
        return price_total_menu;
    }

    public void setPrice_total_menu(int price_total_menu) {
        this.price_total_menu = price_total_menu;
    }

    public String getCategory_menu() {
        return category_menu;
    }

    public void setCategory_menu(String category_menu) {
        this.category_menu = category_menu;
    }
}
