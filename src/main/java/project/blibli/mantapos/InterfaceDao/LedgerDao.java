package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Model.Ledger;
import project.blibli.mantapos.ImplementationDao.BasicDao;

import java.util.List;

public interface LedgerDao extends BasicDao<Ledger, Integer, Integer, Integer, Integer> {
    void createTipeLedger();
    List<Ledger> getListBulanDanTahun(int idResto);
    List<Ledger> getKreditHarian(int idResto, int itemPerPage, int page);

    int getTotalDebitKreditAllTime(int idResto, String tipe);
    int getTotalDebitKreditDalamSebulan(int idResto, int bulan, int tahun, String tipe);
    int getTotalDebitKreditDalamSetahun(int idResto, int tahun, String tipe);
    int getTotalDebitKreditCustom(int idResto, int tanggalAwal, int bulanAwal, int tahunAwal, int tanggalAkhir, int bulanAkhir, int tahunAkhir, String tipe);

    List<Ledger> getLedgerHarian(int idResto, int monthh, int yearr);
    List<Ledger> getLedgerMingguan(int idResto, int monthh, int yearr);
    List<Ledger> getLedgerBulanan(int idResto, int yearr);
    List<Ledger> getLedgerTahunan(int idResto);
    List<Ledger> getLedgerCustom(int idResto, int tanggal1, int month1, int year1, int tanggal2, int month2, int year2);

    List<String> getPenjualanBulananBerdasarkanTahun(int idResto, int year);

    int countDebitAtaukredit(int idResto, String tipe);

}
