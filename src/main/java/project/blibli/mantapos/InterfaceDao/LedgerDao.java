package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Model.Ledger;

import java.util.List;

public interface LedgerDao {
    void CreateTable();
    void CreateTipe();
    void Insert(Ledger ledger, int id_restoo);
    List<Ledger> GetMonthAndYearList(int id_restoo);
    List<Ledger> GetDailyLedger(int id_restoo, int monthh, int yearr);
    List<Ledger> GetWeeklyLedger(int id_restoo, int monthh, int yearr);
    List<Ledger> GetMonthlyLedger(int id_restoo, int yearr);
    List<Ledger> GetDailyKredit(int id_restoo, int itemPerPage, int page);
    int GetTotalDebitBulanan(int id_restoo, int monthh, int yearr);
    int GetTotalDebitTahunan(int id_restoo, int yearr);
    int GetTotalKreditBulanan(int id_restoo, int monthh, int yearr);
    int GetTotalKreditTahunan(int id_restoo, int yearr);
    int GetLastOrderId();
}
