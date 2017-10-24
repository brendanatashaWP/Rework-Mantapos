package project.blibli.mantapos.Beans_Model;

import javax.validation.constraints.NotNull;

public class Restoran {
    private int id;
    private String nama_resto, lokasi_resto;

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
}
