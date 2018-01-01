package project.blibli.mantapos.Model;

import java.sql.Timestamp;

public class Pemasukkan {

    int idResto, biaya;
    Timestamp dateCreated;

    public Pemasukkan(int idResto, int biaya, Timestamp dateCreated) {
        this.idResto = idResto;
        this.biaya = biaya;
        this.dateCreated = dateCreated;
    }

    public Pemasukkan() {
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
