package project.blibli.mantapos.Model;

import java.sql.Timestamp;

public class Saldo {
    private int id_resto;
    private int saldo;
    private String tipe_saldo;
    private Timestamp dateCreated;

    public Saldo() {
    }

    public Saldo(int id_resto, int saldo, String tipe_saldo) {
        this.id_resto = id_resto;
        this.saldo = saldo;
        this.tipe_saldo = tipe_saldo;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTipe_saldo() {
        return tipe_saldo;
    }

    public void setTipe_saldo(String tipe_saldo) {
        this.tipe_saldo = tipe_saldo;
    }

    public int getId_resto() {
        return id_resto;
    }

    public void setId_resto(int id_resto) {
        this.id_resto = id_resto;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
