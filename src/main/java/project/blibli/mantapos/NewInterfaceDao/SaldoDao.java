package project.blibli.mantapos.NewInterfaceDao;

public interface SaldoDao {
    int getSaldoAwal(int idResto, int monthh, int yearr);
    int jumlahBanyakSaldo(int idResto);
    int getSaldoAwalCustom(int idResto, int tanggal1, int month1, int year1);
}
