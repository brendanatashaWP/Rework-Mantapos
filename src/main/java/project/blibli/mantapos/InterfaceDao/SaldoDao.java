package project.blibli.mantapos.InterfaceDao;

public interface SaldoDao {
    void CreateTable();
    void AddSaldoAwal(int id_restoo, int saldoAwal);
    int getSaldoAwal(int id_restoo, int monthh, int yearr);
}
