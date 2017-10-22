package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.DebitKredit;

import java.util.List;

public interface TotalDebitKreditDao {
    void CreateTable();
    int Insert(int tipe, int id_restoo, int monthh, int yearr, int debitKreditAmount);
    int Update(int tipe, int id_restoo, int monthh, int yearr, int debitKreditAmount);
    boolean isDebitKreditExists(int id_restoo, int monthh, int yearr);
    List<DebitKredit> getDebitKreditAmount(int id_restoo, int monthh, int yearr);
}
