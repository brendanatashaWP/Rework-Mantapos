package project.blibli.mantapos.Model;

public class OrderedMenu {
    String nama;
    int qty, price;

    public OrderedMenu(String nama, int price, int qty) {
        this.nama = nama;
        this.price = price;
        this.qty = qty;
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
