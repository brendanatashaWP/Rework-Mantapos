package project.blibli.mantapos.Service.OwnerManager;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Helper.GetIdResto;
import project.blibli.mantapos.ImplementationDao.LedgerDaoImpl;
import project.blibli.mantapos.Model.Ledger;
import project.blibli.mantapos.NewImplementationDao.RestoranDaoImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    LedgerDaoImpl ledgerDao = new LedgerDaoImpl();
    RestoranDaoImpl restoranDao = new RestoranDaoImpl();

    public ModelAndView getMappingDashboard(Authentication authentication) throws SQLException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("owner-manager/dashboard");
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(authentication.getName());
        List<String> dummyLedgerList = getPemasukkanBulanan(idResto, LocalDate.now().getYear());
        mav.addObject("dummyList", dummyLedgerList);
        mav.addObject("total_pengeluaran", getTotalPengeluaranAllTime(idResto));
        mav.addObject("total_pemasukkan", getTotalPemasukkanAllTime(idResto));
        return mav;
    }

    private int getTotalPengeluaranAllTime(int idResto){
        int totalPengeluaranAllTime = ledgerDao.getTotalDebitKreditAllTime(idResto, "kredit");
        return totalPengeluaranAllTime;
    }

    private int getTotalPemasukkanAllTime(int idResto){
        int totalPemasukkanAllTime = ledgerDao.getTotalDebitKreditAllTime(idResto, "debit");
        return totalPemasukkanAllTime;
    }

    private List<String> getPemasukkanBulanan(int idResto,
                                             int year){
        List<String> ledgerList = ledgerDao.getPenjualanBulananBerdasarkanTahun(idResto, year);
        return ledgerList;
    }
}
