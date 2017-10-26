package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.SaldoAwal;

import java.util.List;

public interface SaldoDao {
    void CreateTable();
    void AddSaldoAwal(int id_restoo, int saldoAwal);
    List<SaldoAwal> getSaldoAwalTiapBulan(int id_restoo);
    int getSaldoAwal(int id_restoo, int monthh, int yearr);
}
