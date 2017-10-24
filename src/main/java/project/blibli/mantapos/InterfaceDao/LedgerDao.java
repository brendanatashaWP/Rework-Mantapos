package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.Ledger;

import java.util.List;

public interface LedgerDao {
    void CreateTable();
    void CreateTipe();
    void Insert(Ledger ledger, int id_restoo);
    List<Ledger> GetDailyLedger(int id_restoo, int monthh, int yearr);
    List<Ledger> GetDailyKredit(int id_restoo);
    int GetTotalDebitBulanan(int id_restoo, int monthh, int yearr);
    int GetTotalKreditBulanan(int id_restoo, int monthh, int yearr);
    int GetLastOrderId();
}
