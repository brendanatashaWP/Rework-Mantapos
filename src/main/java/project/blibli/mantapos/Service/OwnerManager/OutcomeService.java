package project.blibli.mantapos.Service.OwnerManager;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Helper.GetIdResto;
import project.blibli.mantapos.ImplementationDao.LedgerDaoImpl;
import project.blibli.mantapos.Model.Ledger;
import project.blibli.mantapos.Model.Saldo;
import project.blibli.mantapos.Helper.WeekGenerator;
import project.blibli.mantapos.NewImplementationDao.SaldoAkhirDaoImpl;
import project.blibli.mantapos.NewImplementationDao.SaldoAwalDaoImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OutcomeService {

    LedgerDaoImpl ledgerDao = new LedgerDaoImpl();
    SaldoAwalDaoImpl saldoAwalDao = new SaldoAwalDaoImpl();
    SaldoAkhirDaoImpl saldoAkhirDao = new SaldoAkhirDaoImpl();
    int itemPerPage=5;
    int tanggal = LocalDate.now().getDayOfMonth();
    int bulan = LocalDate.now().getMonthValue();
    int tahun = LocalDate.now().getYear();

    public ModelAndView getMappingOutcome(Authentication authentication,
                                          Integer page) throws SQLException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("owner-manager/outcome");
        if(page == null){
            page=1;
        }
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(authentication.getName());
        double jumlahBanyakOutcome = getCountOutcome(idResto);
        double jumlahPage = Math.ceil(jumlahBanyakOutcome/itemPerPage); //Menghitung jumlahPage, didapatkan dari jumlah row outcome dibagi dengan item per page. Misal jumlah row outcome 12 dan item per page 5, maka akan ada 3 page dengan item-nya 5,5,3
        List<Integer> pageList = new ArrayList<>(); //List untuk menampung nilai-nilai page (jika jumlahPage = 3, maka di pageList ini isinya 1,2,3
        for (int i=1; i<=jumlahPage; i++){
            pageList.add(i);
        }
        mav.addObject("outcomeList", getPengeluaranHarian(idResto, page));
        mav.addObject("pageNo", page);
        mav.addObject("pageList", pageList);
        return mav;
    }

    public ModelAndView postMappingOutcome(Authentication authentication,
                                           Ledger ledger,
                                           String qty) throws SQLException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/outcome");
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(authentication.getName());
        insertNewPengeluaran(idResto, ledger, qty);
        updateSaldoAkhir(idResto);
        return mav;
    }

    private void insertNewPengeluaran(int idResto,
                                      Ledger ledger,
                                      String qty){
        String[] dateSplit = ledger.getWaktu().split("-"); //split date dengan character "-" dari date yang di-pick dari user melalui outcome.html (formatnya yyyy-mm-dd)
        int tanggal = Integer.parseInt(dateSplit[2]);
        ledger.setTanggal(tanggal); //tanggal itu ada di elemen ke-2
        int week = WeekGenerator.GetWeek(tanggal);
        ledger.setWeek(week); //week di-generate melalui class WeekGenerator, lalu setWeek di model ledger
        int month = Integer.parseInt(dateSplit[1]);
        ledger.setMonth(month); //month itu ada di elemen ke-1, lalu setMonth
        int year = Integer.parseInt(dateSplit[0]);
        ledger.setYear(year); //year itu ada di elemen ke-0, lalu setYear
        ledger.setTipe("kredit"); //karena ini adalah pengeluaran (outcome), berarti tipenya adalah kredit
        ledger.setKeperluan(ledger.getKeperluan() + "(" + qty + ")"); //Keperluannya adalah diambil dari keperluan yang diinputkan oleh user ditambahkan dengan quantity yang diinputkan oleh user
        ledgerDao.insert(ledger, idResto);
    }

    private void updateSaldoAkhir(int idResto) throws SQLException {
        Saldo saldo = new Saldo();
        saldo.setId_resto(idResto);
        saldo.setTipe_saldo("akhir");
        saldo.setTanggal(tanggal);
        saldo.setMonth(bulan);
        saldo.setYear(tahun);
        int saldoAwal = saldoAwalDao.getOne("id_resto=" + idResto).getSaldo();
        saldo.setSaldo(saldoAwal + ledgerDao.getTotalDebitKreditDalamSebulan(idResto, bulan, tahun, "debit") - ledgerDao.getTotalDebitKreditDalamSebulan(idResto, bulan, tahun, "kredit")); //saldo akhir = saldo awal + debit - kredit
        if(saldoAkhirDao.count("id_resto=" + idResto)==0){
            saldoAkhirDao.insert(saldo);
        } else{
            saldoAkhirDao.update(saldo, "id_resto=" + idResto);
        }
    }

    private List<Ledger> getPengeluaranHarian(int idResto,
                                              int page){
        List<Ledger> pengeluaranHarianList = ledgerDao.getKreditHarian(idResto, itemPerPage, page);
        return pengeluaranHarianList;
    }

    private int getCountOutcome(int idResto){
        int countOutcome = ledgerDao.countDebitAtaukredit(idResto, "kredit");
        return countOutcome;
    }

}
