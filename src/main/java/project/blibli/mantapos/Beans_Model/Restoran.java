package project.blibli.mantapos.Beans_Model;

import javax.validation.constraints.NotNull;

public class Restoran {
    private int id;
    private String nama_resto, lokasi_resto;
    private String username, password;
    private String nama_lengkap, nomor_ktp, nomor_telepon, alamat;
    private int id_user;

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_resto() {
        return nama_resto;
    }

    public void setNama_resto(String nama_resto) {
        this.nama_resto = nama_resto;
    }

    public String getLokasi_resto() {
        return lokasi_resto;
    }

    public void setLokasi_resto(String lokasi_resto) {
        this.lokasi_resto = lokasi_resto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getNomor_ktp() {
        return nomor_ktp;
    }

    public void setNomor_ktp(String nomor_ktp) {
        this.nomor_ktp = nomor_ktp;
    }

    public String getNomor_telepon() {
        return nomor_telepon;
    }

    public void setNomor_telepon(String nomor_telepon) {
        this.nomor_telepon = nomor_telepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
