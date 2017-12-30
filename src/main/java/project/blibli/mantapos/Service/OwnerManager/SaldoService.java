package project.blibli.mantapos.Service.OwnerManager;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Helper.GetIdResto;
import project.blibli.mantapos.ImplementationDao.SaldoDaoImpl;
import project.blibli.mantapos.Model.Saldo;
import project.blibli.mantapos.Helper.MonthNameGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaldoService {

    SaldoDaoImpl saldoDao = new SaldoDaoImpl();
    int itemPerPage=5;

    int tanggal = LocalDate.now().getDayOfMonth();
    int intBulan = LocalDate.now().getMonthValue();
    int tahun = LocalDate.now().getYear();
    String bulan = MonthNameGenerator.MonthNameGenerator(intBulan); //Mengambil nama bulan berdasarkan nilai integer bulan-nya

    public ModelAndView getMappingSaldoAwal(Authentication authentication,
                                            Integer page){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("owner-manager/saldo-awal");
        if (page == null){
            page = 1;
        }
        mav.addObject("pageNo", page);
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(authentication.getName());
        double jumlahBanyakSaldo = getCountSaldoAwal(idResto);
        double jumlahPage = Math.ceil(jumlahBanyakSaldo/itemPerPage);
        List<Integer> pageList = new ArrayList<>();
        for (int i=1; i<=jumlahPage; i++){
            pageList.add(i);
        }
        mav.addObject("pageList", pageList);
        mav.addObject("saldoAwalList", getSaldoAwalWithBulanTahunAndPagination(idResto, page));
        mav.addObject("bulan", bulan);
        mav.setViewName("owner-manager/saldo-awal");
        return mav;
    }

    public ModelAndView postMappingAddSaldoAwal(Authentication authentication,
                                                Saldo saldo){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/saldo");
        saldo.setTipe_saldo("awal");
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(authentication.getName());
        insertSaldoAwal(idResto, saldo);
        return mav;
    }

    private void insertSaldoAwal(int idResto,
                                Saldo saldo){
        saldo.setTanggal(tanggal);
        saldo.setMonth(intBulan);
        saldo.setYear(tahun);
        saldoDao.insert(saldo, idResto);
    }

    private int getSaldoAwalRestoran(int idResto){
        int saldoAwal = saldoDao.getSaldoAwal(idResto);
        return saldoAwal;
    }

    private List<Saldo> getSaldoAwalWithBulanTahunAndPagination(int idResto,
                                                               int page){
        List<Saldo> saldoList = saldoDao.readAll(idResto, 5, page);
        for (Saldo saldo : saldoList){
            System.out.println("Saldo awal : " + saldo.getSaldo());
        }
        return saldoList;
    }

    private int getCountSaldoAwal(int idResto){
        int countSaldoAwalResto = saldoDao.count(idResto);
        return countSaldoAwalResto;
    }

}
