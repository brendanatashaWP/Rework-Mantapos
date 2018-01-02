package project.blibli.mantapos.Model;

import java.sql.Timestamp;

public class Pengeluaran {

    int idResto, biaya;
    Timestamp dateCreated;
    String keperluan, quantity;

    public Pengeluaran() {
    }

    public Pengeluaran(int idResto, int biaya, String keperluan, String quantity) {
        this.idResto = idResto;
        this.biaya = biaya;
        this.keperluan = keperluan;
        this.quantity = quantity;
    }

    public String getKeperluan() {
        return keperluan;
    }

    public void setKeperluan(String keperluan) {
        this.keperluan = keperluan;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getIdResto() {
        return idResto;
    }

    public void setIdResto(int idResto) {
        this.idResto = idResto;
    }

    public int getBiaya() {
        return biaya;
    }

    public void setBiaya(int biaya) {
        this.biaya = biaya;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }
}
