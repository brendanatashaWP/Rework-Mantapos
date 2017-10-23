package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Beans_Model.*;
import project.blibli.mantapos.ImplementationDao.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ManagerController {

    private OrderDaoImpl orderDao = new OrderDaoImpl();
    private SaldoDaoImpl saldoDao = new SaldoDaoImpl();
    private RestaurantDaoImpl restaurantDao = new RestaurantDaoImpl();
    private TotalDebitKreditDaoImpl debitKreditDao = new TotalDebitKreditDaoImpl();
    Restaurant restaurant;

    @GetMapping(value = "/dashboard", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView managerDashboardHtml(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("manager-dashboard");
        return mav;
    }
    @GetMapping(value = "/dashboard-menu", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView menuDashboardHtml(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("manager-menu");
        return mav;
    }

//    @PostMapping(value = "/add-saldo-awal", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Map<String, String> addSaldoAwalJson(@RequestParam("saldo_awal") int saldo_awal,
//                                                Authentication authentication){
//        Map<String, String> param = new HashMap<>();
//        //Ambil username yang sedang login, untuk nantinya diambil ID restaurant-nya
//        String loggedInUsername = authentication.getName();
//
//        Restaurant restaurant = restaurantDao.GetRestaurantInfo(loggedInUsername);
//        int statusAddSaldoAwal = saldoDao.AddSaldoAwal(restaurant.getId_restaurant(), saldo_awal);
//        if(statusAddSaldoAwal==1)
//            param.put("status", "success");
//        else
//            param.put("status", "failed");
//        return param;
//    }

    @GetMapping(value = "/ledger-choose-range", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView ledgerChooseRangeHtml(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("manager-pilih-range-ledger");
        return mav;
    }

    @PostMapping(value = "/ledger-range-daily")
    //get bulan yang dikehendaki di tahun LocalDate.now().getYear()
    public ModelAndView getWeekly(@ModelAttribute("income") Income income,
                                  Authentication authentication){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("manager-ledger-daily");

        String username = authentication.getName();
        restaurant = restaurantDao.GetRestaurantInfo(username);

        int saldo_awal = saldoDao.getSaldoAwal(restaurant.getId_restaurant(), income.getMonth(), LocalDate.now().getYear());
        mav.addObject("saldo_awal", saldo_awal);

        int saldo_akhir = saldoDao.getSaldoAkhir(restaurant.getId_restaurant(), income.getMonth(), LocalDate.now().getYear());
        mav.addObject("saldo_akhir", saldo_akhir);

        int mutasi = saldo_awal-saldo_akhir;
        mav.addObject("mutasi", mutasi);

        List<DebitKredit> debitKreditList = debitKreditDao.getDebitKreditAmount(restaurant.getId_restaurant(), income.getMonth(), LocalDate.now().getYear());
        int total_debit=0, total_kredit=0;
        for (DebitKredit item:debitKreditList
             ) {
            total_debit = item.getDebit();
            total_kredit = item.getKredit();
        }
        mav.addObject("total_debit", total_debit);
        mav.addObject("total_kredit", total_kredit);

        List<Order> orderList = orderDao.getOrderPriceTotal(restaurant.getId_restaurant(), income.getMonth(), LocalDate.now().getYear());
        mav.addObject("orderList", orderList);
//        List<DailyIncome> dailyIncomeList = incomeDao.getDailyIncome(restaurant.getId_restaurant(), income.getMonth(), LocalDate.now().getYear());

        return mav;
    }
}
