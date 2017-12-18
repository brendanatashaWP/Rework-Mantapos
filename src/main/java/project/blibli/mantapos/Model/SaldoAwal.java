package project.blibli.mantapos.Model;

public class SaldoAwal {
    private int id_resto, month, year;
    private double saldo_awal;

    public int getId_resto() {
        return id_resto;
    }

    public void setId_resto(int id_resto) {
        this.id_resto = id_resto;
    }

    public double getSaldo_awal() {
        return saldo_awal;
    }

    public void setSaldo_awal(double saldo_awal) {
        this.saldo_awal = saldo_awal;
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
