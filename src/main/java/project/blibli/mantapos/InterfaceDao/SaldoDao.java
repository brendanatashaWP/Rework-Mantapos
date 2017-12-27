package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Model.SaldoAwal;

import java.util.List;

public interface SaldoDao {
    void CreateTable();
    void AddSaldoAwal(int id_restoo, double saldoAwal);
    List<SaldoAwal> getSaldoAwalTiapBulan(int id_restoo, int itemPerPage, int page);
    int getSaldoAwal(int id_restoo, int monthh, int yearr);
    int jumlahBanyakSaldo(int id_restoo);
    int getSaldoAwalCustom(int id_restoo, int tanggal1, int month1, int year1);
}
