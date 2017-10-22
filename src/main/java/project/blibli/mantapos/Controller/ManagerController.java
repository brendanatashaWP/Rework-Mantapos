package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Beans_Model.Income;
import project.blibli.mantapos.Beans_Model.Restaurant;
import project.blibli.mantapos.ImplementationDao.IncomeDaoImpl;
import project.blibli.mantapos.ImplementationDao.RestaurantDaoImpl;
import project.blibli.mantapos.ImplementationDao.SaldoDaoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ManagerController {

    private IncomeDaoImpl incomeDao = new IncomeDaoImpl();
    private SaldoDaoImpl saldoDao = new SaldoDaoImpl();
    private RestaurantDaoImpl restaurantDao = new RestaurantDaoImpl();

    @GetMapping(value = "/manager", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView managerDashboardHtml(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin-dashboard");
        return mav;
    }

    @PostMapping(value = "/add-saldo-awal", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addSaldoAwalJson(@RequestParam("saldo_awal") int saldo_awal,
                                                Authentication authentication){
        Map<String, String> param = new HashMap<>();
        //Ambil username yang sedang login, untuk nantinya diambil ID restaurant-nya
        String loggedInUsername = authentication.getName();

        Restaurant restaurant = restaurantDao.GetRestaurantInfo(loggedInUsername);
        int statusAddSaldoAwal = saldoDao.AddSaldoAwal(restaurant.getId_restaurant(), saldo_awal);
        if(statusAddSaldoAwal==1)
            param.put("status", "success");
        else
            param.put("status", "failed");
        return param;
    }

    @GetMapping(value = "/ledger/daily/{month}")
    //get bulan yang dikehendaki di tahun LocalDate.now().getYear()
    public Map<String, List<Integer>> incomeJson(@PathVariable("month") String month){
        Map<String, List<Integer>> param = new HashMap<>();
        List<Integer> incomeList = new ArrayList<>();
        param.put("RESULT", incomeList);
        return param;
    }
}
