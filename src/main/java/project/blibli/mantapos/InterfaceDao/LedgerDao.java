package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Model.Ledger;
import project.blibli.mantapos.ImplementationDao.BasicDao;

import java.util.List;

public interface LedgerDao extends BasicDao<Ledger, Integer, Integer, Integer, Integer> {
    void createTipeLedger();
    List<Ledger> getListBulanDanTahun(int idResto);
    List<Ledger> getLedgerHarian(int idResto, int monthh, int yearr);
    List<Ledger> getLedgerMingguan(int idResto, int monthh, int yearr);
    List<Ledger> getLedgerBulanan(int idResto, int yearr);
    List<Ledger> getLedgerCustom(int idResto, int tanggal1, int month1, int year1, int tanggal2, int month2, int year2);
    List<Ledger> getKreditHarian(int idResto, int itemPerPage, int page);
    int getTotalDebitBulanan(int idResto, int monthh, int yearr);
    int getTotalDebitTahunan(int idResto, int yearr);
    int getTotalDebitCustom(int idResto, int tanggal1, int month1, int year1, int tanggal2, int month2, int year2);
    int getTotalKreditBulanan(int idResto, int monthh, int yearr);
    int getTotalKreditTahunan(int idResto, int yearr);
    int getTotalKreditCustom(int idResto, int tanggal1, int month1, int year1, int tanggal2, int month2, int year2);

    int getTotalDebitAllTime(int idResto);
    int getTotalKreditAllTime(int idResto);

    List<String> getPenjualanBulananBerdasarkanTahun(int idResto, int year);

    int countDebitAtaukredit(int idResto, String tipe);

}
