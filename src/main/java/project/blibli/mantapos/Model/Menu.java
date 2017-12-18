package project.blibli.mantapos.Model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Menu {
    private int id;
    @NotBlank
    private String nama_menu;
    private String lokasi_gambar_menu;
    @NotBlank
    private String kategori_menu;
    @NotNull
    @Min(0)
    private double harga_menu;
    private MultipartFile multipartFile;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

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

    public double getHarga_menu() {
        return harga_menu;
    }

    public void setHarga_menu(double harga_menu) {
        this.harga_menu = harga_menu;
    }

    public String getKategori_menu() {
        return kategori_menu;
    }

    public void setKategori_menu(String kategori_menu) {
        this.kategori_menu = kategori_menu;
    }
}
