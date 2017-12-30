package project.blibli.mantapos.Service.OwnerManager;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.GetIdResto;
import project.blibli.mantapos.ImplementationDao.LedgerDaoImpl;
import project.blibli.mantapos.ImplementationDao.SaldoDaoImpl;
import project.blibli.mantapos.Model.Ledger;

import java.util.ArrayList;
import java.util.List;

@Service
public class LedgerService {

    LedgerDaoImpl ledgerDao = new LedgerDaoImpl();
    SaldoDaoImpl saldoDao = new SaldoDaoImpl();

    public ModelAndView getMappingChooseRangeLedger(Authentication authentication){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("owner-manager/pilih-range-ledger");
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(authentication.getName());
        mav.addObject("monthAndYearList", getListBulanDanTahun(idResto));
        return mav;
    }

    public ModelAndView postMappingLihatLedger(Authentication authentication,
                                               String skalaLedger,
                                               int bulanTerpilih,
                                               int tahunTerpilih,
                                               String ledgerCustomWaktuAwal,
                                               String ledgerCustomWaktuAkhir){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("owner-manager/ledger");
        List<Ledger> ledgerList = new ArrayList<>();
        int saldoAwalBulanTerpilih = 0, saldoAkhirBulanTerpilih=0, mutasi=0, totalDebit=0, totalKredit=0;
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(authentication.getName());
        String skalaLedgerPassToHtml = null;
        if(skalaLedger.equals("harian") || skalaLedger.equals("mingguan")){
            saldoAwalBulanTerpilih = getSaldoAkhirBulanLaluSebagaiSaldoAwalBulanTerpilih(idResto, bulanTerpilih, tahunTerpilih);
            totalDebit = getTotalDebitDalamSebulan(idResto, bulanTerpilih, tahunTerpilih);
            totalKredit = getTotalKreditDalamSebulan(idResto, bulanTerpilih, tahunTerpilih);
            saldoAkhirBulanTerpilih = hitungSaldoAkhirSekarang(saldoAwalBulanTerpilih, totalDebit, totalKredit);
            mutasi = hitungMutasi(saldoAkhirBulanTerpilih, saldoAwalBulanTerpilih);
            if(skalaLedger.equals("harian")){
                ledgerList = getLedgerHarianDalamSebulan(idResto, bulanTerpilih, tahunTerpilih);
                skalaLedgerPassToHtml = "HARIAN";
            } else{
                ledgerList = getLedgerMingguanDalamSebulan(idResto, bulanTerpilih, tahunTerpilih);
                skalaLedgerPassToHtml = "MINGGUAN";
            }
        } else if(skalaLedger.equals("bulanan")){
            int bulanPalingAwal = getBulanPalingAwalDalamTahunTerpilih(idResto, tahunTerpilih);
            saldoAwalBulanTerpilih = getSaldoAkhirBulanLaluSebagaiSaldoAwalBulanTerpilih(idResto, bulanPalingAwal, tahunTerpilih);
            totalDebit = getTotalDebitDalamSetahun(idResto, tahunTerpilih);
            totalKredit = getTotalKreditDalamSetahun(idResto, tahunTerpilih);
            saldoAkhirBulanTerpilih = hitungSaldoAkhirSekarang(saldoAwalBulanTerpilih, totalDebit, totalKredit);
            mutasi = hitungMutasi(saldoAkhirBulanTerpilih, saldoAwalBulanTerpilih);
            ledgerList = getLedgerBulananDalamSetahun(idResto, tahunTerpilih);
            skalaLedgerPassToHtml = "BULANAN";
        } else if(skalaLedger.equals("tahunan")){
            //TODO : ledger tahunan
        } else {
            String[] dateAwalSplit = ledgerCustomWaktuAwal.split("-");
            int tanggalAwal = Integer.parseInt(dateAwalSplit[2]);
            int bulanAwal = Integer.parseInt(dateAwalSplit[1]);
            int tahunAwal = Integer.parseInt(dateAwalSplit[0]);
            String[] dateAkhirSplit = ledgerCustomWaktuAkhir.split("-");
            int tanggalAkhir = Integer.parseInt(dateAkhirSplit[2]);
            int bulanAkhir = Integer.parseInt(dateAkhirSplit[1]);
            int tahunAkhir = Integer.parseInt(dateAkhirSplit[0]);

            saldoAwalBulanTerpilih = getSaldoAkhirBulanLaluSebagaiSaldoAwalBulanTerpilih(idResto, bulanAwal, tahunAwal);
            totalDebit = getTotalDebitCustom(idResto, tanggalAwal, bulanAwal, tahunAwal, tanggalAkhir, bulanAkhir, tahunAkhir);
            totalKredit = getTotalKreditCustom(idResto, tanggalAwal, bulanAwal, tahunAwal, tanggalAkhir, bulanAkhir, tahunAkhir);
            saldoAkhirBulanTerpilih = hitungSaldoAkhirSekarang(saldoAwalBulanTerpilih, totalDebit, totalKredit);
            mutasi = hitungMutasi(saldoAkhirBulanTerpilih, saldoAwalBulanTerpilih);
            ledgerList = getLedgerCustomRange(idResto, tanggalAwal, bulanAwal, tahunAwal, tanggalAkhir, bulanAkhir, tahunAkhir);
            skalaLedgerPassToHtml = "CUSTOM";
        }
        mav.addObject("saldo_awal", saldoAwalBulanTerpilih);
        mav.addObject("total_debit", totalDebit);
        mav.addObject("total_kredit", totalKredit);
        mav.addObject("saldo_akhir", saldoAkhirBulanTerpilih);
        mav.addObject("mutasi", mutasi);
        mav.addObject("ledgerList", ledgerList);
        mav.addObject("skala_ledger", skalaLedgerPassToHtml);
        return mav;
    }

    private List<Ledger> getListBulanDanTahun(int idResto){
        List<Ledger> bulanDanTahunList = ledgerDao.getListBulanDanTahun(idResto);
        return bulanDanTahunList;
    }

    private int getTotalDebitDalamSebulan(int idResto,
                                     int month,
                                     int year){
        int totalDebitDalamSebulan = ledgerDao.getTotalDebitDalamSebulan(idResto, month, year);
        return totalDebitDalamSebulan;
    }

    private int getTotalKreditDalamSebulan(int idResto,
                                      int month,
                                      int year){
        int totalKreditDalamSebulan = ledgerDao.getTotalKreditDalamSebulan(idResto, month, year);
        return totalKreditDalamSebulan;
    }

    private int getTotalDebitDalamSetahun(int idResto,
                                          int year){
        int totalDebitDalamSetahun = ledgerDao.getTotalDebitDalamSetahun(idResto, year);
        return totalDebitDalamSetahun;
    }

    private int getTotalKreditDalamSetahun(int idResto,
                                           int year){
        int totalKreditDalamSetahun = ledgerDao.getTotalKreditDalamSetahun(idResto, year);
        return totalKreditDalamSetahun;
    }

    private int getTotalDebitCustom(int idResto,
                                    int tanggalAwal,
                                    int bulanAwal,
                                    int tahunAwal,
                                    int tanggalAkhir,
                                    int bulanAkhir,
                                    int tahunAkhir){
        int totalDebitCustom = ledgerDao.getTotalDebitCustom(idResto, tanggalAwal, bulanAwal, tahunAwal, tanggalAkhir, bulanAkhir, tahunAkhir);
        return totalDebitCustom;
    }

    private int getTotalKreditCustom(int idResto,
                                     int tanggalAwal,
                                     int bulanAwal,
                                     int tahunAwal,
                                     int tanggalAkhir,
                                     int bulanAkhir,
                                     int tahunAkhir){
        int totalKreditCustom = ledgerDao.getTotalKreditCustom(idResto, tanggalAwal, bulanAwal, tahunAwal, tanggalAkhir, bulanAkhir, tahunAkhir);
        return totalKreditCustom;
    }

    private int getSaldoAwalBanget(int idResto){
        int saldoAwalBanget = saldoDao.getSaldoAwal(idResto);
        return saldoAwalBanget;
    }

    private int getSaldoAkhirBulanLaluSebagaiSaldoAwalBulanTerpilih(int idResto,
                                                                    int bulanTerpilih,
                                                                    int tahunTerpilih){
        int bulanLalu=0, saldoAkhirBulanLalu;
        if(bulanTerpilih==1){
            //jika bulan yg dipilih adalah januari, berarti bulan lalu adalah bulan desember tahun sebelumnya
            bulanLalu=12;
            tahunTerpilih=tahunTerpilih-1;
        } else{
            bulanLalu=bulanTerpilih-1;
        }
        saldoAkhirBulanLalu = saldoDao.getSaldoAkhir(idResto, bulanLalu, tahunTerpilih);
        if(saldoAkhirBulanLalu==0){
            //jika saldo akhir bulan lalu gak ada nilainya, artinya bulan yang terpilih sekarang adalah satu2nya bulan di database
            saldoAkhirBulanLalu = getSaldoAwalBanget(idResto);
        }
        return saldoAkhirBulanLalu;
    }

    private List<Ledger> getLedgerHarianDalamSebulan(int idResto,
                                         int bulanTerpilih,
                                         int tahunTerpilih){
        List<Ledger> ledgerList = ledgerDao.getLedgerHarian(idResto, bulanTerpilih, tahunTerpilih);
        return ledgerList;
    }

    private List<Ledger> getLedgerMingguanDalamSebulan(int idResto,
                                                       int bulanTerpilih,
                                                       int tahunTerpilih){
        List<Ledger> ledgerList = ledgerDao.getLedgerMingguan(idResto, bulanTerpilih, tahunTerpilih);
        return ledgerList;
    }

    private List<Ledger> getLedgerBulananDalamSetahun(int idResto,
                                          int tahunTerpilih){
        List<Ledger> ledgerList = ledgerDao.getLedgerBulanan(idResto, tahunTerpilih);
        return ledgerList;
    }

    private List<Ledger> getLedgerCustomRange(int idResto,
                                              int tanggalAwal,
                                              int bulanAwal,
                                              int tahunAwal,
                                              int tanggalAkhir,
                                              int bulanAkhir,
                                              int tahunAkhir){
        List<Ledger> ledgerList = ledgerDao.getLedgerCustom(idResto, tanggalAwal, bulanAwal, tahunAwal, tanggalAkhir, bulanAkhir, tahunAkhir);
        return ledgerList;
    }

    private int hitungSaldoAkhirSekarang(int saldoAwal,
                                         int totalDebit,
                                         int totalKredit){
        int saldoAkhirSekarang = saldoAwal+totalDebit-totalKredit;
        return saldoAkhirSekarang;
    }

    private int hitungMutasi(int saldoAkhir,
                             int saldoAwal){
        int mutasi = saldoAkhir-saldoAwal;
        return mutasi;
    }

    private int getBulanPalingAwalDalamTahunTerpilih(int idResto,
                                                   int yearTerpilih){
        int bulanTerkecil = saldoDao.getMinMonthInYear(idResto, yearTerpilih);
        return bulanTerkecil;
    }

}
