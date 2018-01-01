package project.blibli.mantapos.Model;

import java.sql.Timestamp;

public class Saldo {
    private int id_resto, tanggal, month, year;
    private int saldo;
    private String tipe_saldo;
    private Timestamp date_created;

    public Saldo() {
    }

    public Saldo(int id_resto, int saldo, String tipe_saldo) {
        this.id_resto = id_resto;
        this.saldo = saldo;
        this.tipe_saldo = tipe_saldo;
    }

    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
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

    public int getTanggal() {
        return tanggal;
    }

    public void setTanggal(int tanggal) {
        this.tanggal = tanggal;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
