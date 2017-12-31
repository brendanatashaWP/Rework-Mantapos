package project.blibli.mantapos.Model;

public class OrderedMenu {
    int idOrder, idMenu;
    String nama;
    int qty, price;

    public OrderedMenu(int idOrder, int idMenu, String nama, int price, int qty) {
        this.idOrder = idOrder;
        this.idMenu = idMenu;
        this.nama = nama;
        this.price = price;
        this.qty = qty;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
