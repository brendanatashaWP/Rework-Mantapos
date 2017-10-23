package project.blibli.mantapos.InterfaceDao;

public interface SaldoDao {
    void CreateTable();
    int AddSaldoAwal(int id_restoo, int saldoAwal);
    int Update(int tipe, int id_resto, int monthh, int yearr, int amount);
    int getSaldoAkhir(int id_resto, int monthh, int yearr);
    int getSaldoAwal(int id_restoo, int monthh, int yearr);
}
