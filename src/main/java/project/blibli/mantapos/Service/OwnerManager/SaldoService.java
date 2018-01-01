package project.blibli.mantapos.Service.OwnerManager;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Helper.GetIdResto;
import project.blibli.mantapos.Model.Saldo;
import project.blibli.mantapos.Helper.MonthNameGenerator;
import project.blibli.mantapos.NewImplementationDao.SaldoAkhirDaoImpl;
import project.blibli.mantapos.NewImplementationDao.SaldoAwalDaoImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaldoService {

    SaldoAwalDaoImpl saldoAwalDao = new SaldoAwalDaoImpl();
    SaldoAkhirDaoImpl saldoAkhirDao = new SaldoAkhirDaoImpl();
    int itemPerPage=5;

    int tanggal = LocalDate.now().getDayOfMonth();
    int intBulan = LocalDate.now().getMonthValue();
    int tahun = LocalDate.now().getYear();
    String bulan = MonthNameGenerator.MonthNameGenerator(intBulan); //Mengambil nama bulan berdasarkan nilai integer bulan-nya

    public ModelAndView getMappingSaldoAwal(Authentication authentication,
                                            Integer page) throws SQLException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("owner-manager/saldo-awal");
        if (page == null){
            page = 1;
        }
        mav.addObject("pageNo", page);
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(authentication.getName());
        mav.addObject("saldoAwalList", getSaldoAwalWithBulanTahunAndPagination(idResto, page));
        mav.addObject("bulan", bulan);
        mav.setViewName("owner-manager/saldo-awal");
        return mav;
    }

    public ModelAndView postMappingAddSaldoAwal(Authentication authentication,
                                                Saldo saldo) throws SQLException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/saldo");
        saldo.setTipe_saldo("awal");
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(authentication.getName());
        insertSaldoAwal(idResto, saldo);
        return mav;
    }

    private void insertSaldoAwal(int idResto,
                                 Saldo saldo) throws SQLException {
        saldo.setId_resto(idResto);
        saldo.setTanggal(tanggal);
        saldo.setMonth(intBulan);
        saldo.setYear(tahun);
        saldoAwalDao.insert(saldo);
    }

    private int getSaldoAwalRestoran(int idResto) throws SQLException {
        int saldoAwal = saldoAwalDao.getOne("id_resto=" + idResto).getSaldo();
        return saldoAwal;
    }

    private List<Saldo> getSaldoAwalWithBulanTahunAndPagination(int idResto,
                                                               int page) throws SQLException {
        List<Saldo> saldoList = saldoAwalDao.getAll("id_resto=" + idResto + " LIMIT " + itemPerPage + " OFFSET " + (page-1)*itemPerPage);
        return saldoList;
    }

}
