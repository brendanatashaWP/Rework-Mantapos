package project.blibli.mantapos.Model;

public class OrderedMenu {
    String nama, price;
    int qty;

    public OrderedMenu(String nama, String price, int qty) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
