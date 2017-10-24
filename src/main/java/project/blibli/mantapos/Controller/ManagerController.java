package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Beans_Model.*;
import project.blibli.mantapos.ImplementationDao.*;
import project.blibli.mantapos.WeekGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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
    private IncomeDaoImpl incomeDao = new IncomeDaoImpl();
    private OutcomeDaoImpl outcomeDao = new OutcomeDaoImpl();
    Restaurant restaurant;

    @GetMapping(value = "/dashboard", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView managerDashboardHtml(){
        return new ModelAndView("manager-dashboard");
    }
    @GetMapping(value = "/dashboard-menu", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView menuDashboardHtml(){
        return new ModelAndView("manager-menu");
    }
    @GetMapping(value = "/outcome", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView outcomeHtml(){
        return new ModelAndView("manager-outcome");
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
        return new ModelAndView("manager-pilih-range-ledger");
    }

    @PostMapping(value = "/outcome-post", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView outcomeHtmlPost(@ModelAttribute("outcome") Outcome outcome,
                                        Authentication authentication){
        String username = authentication.getName();
        restaurant = restaurantDao.GetRestaurantInfo(username);
        String[] dateSplit = outcome.getOutcome_date().split("-");
        int tanggal = Integer.parseInt(dateSplit[2]);
        int week = WeekGenerator.GetWeek(tanggal); outcome.setWeek(week);
        int month = Integer.parseInt(dateSplit[1]); outcome.setMonth(month);
        int year = Integer.parseInt(dateSplit[0]); outcome.setYear(year);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String jam = dtf.format(now);
        outcome.setOutcome_date(outcome.getOutcome_date() + "," + jam);
        System.out.println("Week : " + week + ", Month : " + month + ", Year : " + year);
        int status = outcomeDao.Insert(restaurant.getId_restaurant(), outcome);
        boolean isIncomeExists = incomeDao.isIncomeExists(restaurant.getId_restaurant());
        if(!isIncomeExists){
            debitKreditDao.Insert(1, restaurant.getId_restaurant(), LocalDate.now().getMonthValue(), LocalDateTime.now().getYear(), outcome.getOutcome_amount());
        } else{
            debitKreditDao.Update(1, restaurant.getId_restaurant(), LocalDate.now().getMonthValue(), LocalDateTime.now().getYear(), outcome.getOutcome_amount());
            saldoDao.Update(1, restaurant.getId_restaurant(), LocalDate.now().getMonthValue(), LocalDate.now().getYear(), outcome.getOutcome_amount());
        }
        return new ModelAndView("redirect:/outcome");
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
        return mav;
    }
}
