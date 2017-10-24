package project.blibli.mantapos.Beans_Model;

public class Menu {
    private int id;
    private String nama_menu, lokasi_gambar_menu, kategori_menu;
    private int harga_menu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getLokasi_gambar_menu() {
        return lokasi_gambar_menu;
    }

    public void setLokasi_gambar_menu(String lokasi_gambar_menu) {
        this.lokasi_gambar_menu = lokasi_gambar_menu;
    }

    public int getHarga_menu() {
        return harga_menu;
    }

    public void setHarga_menu(int harga_menu) {
        this.harga_menu = harga_menu;
    }

    public String getKategori_menu() {
        return kategori_menu;
    }

    public void setKategori_menu(String kategori_menu) {
        this.kategori_menu = kategori_menu;
    }
}
