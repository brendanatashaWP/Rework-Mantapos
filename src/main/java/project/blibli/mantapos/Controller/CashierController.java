package project.blibli.mantapos.Controller;

import com.sun.tools.corba.se.idl.constExpr.Or;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Beans_Model.Menu;
import project.blibli.mantapos.Beans_Model.Order;
import project.blibli.mantapos.Dao.MenuDao;
import project.blibli.mantapos.Dao.OrderDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CashierController {

    @GetMapping(value = "/cashier", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView cashierHtml(){
        List<Menu> menuList = MenuDao.getAll();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("cashier");
        mav.addObject("menuList", menuList);
        return mav;
    }

    @GetMapping(value = "/cashier", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Menu>> cashierJson(){
        Map<String, List<Menu>> param = new HashMap<>();
        List<Menu> menuList = MenuDao.getAll();
        param.put("menu", menuList);
        return param;
    }

    @PostMapping(value = "/add-order", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addOrderJson(@ModelAttribute("order") Order order){
        Map<String, String> param = new HashMap<>();
        int status = OrderDao.Insert(order);
        if (status==1){
            param.put("status", "success!");
            param.put("customer name", order.getCustomerName());
            param.put("table no", String.valueOf(order.getTableNo()));
            param.put("price total", String.valueOf(order.getPriceTotal()));
            param.put("notes", order.getNotes());
            //order time, week, month, dan year return NULL
            //karena kan di model order itu kosong value mereka, value mereka di assign
            //di OrderDao tanpa harus ke model
            param.put("order time", order.getOrdered_time());
            param.put("week", String.valueOf(order.getWeek()));
            param.put("month", String.valueOf(order.getMonth()));
            param.put("year", String.valueOf(order.getYear()));
        } else{
            param.put("status", "failed!");
        }
        return param;
    }

    @PostMapping(value = "/add-order", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addOrderHtml(@ModelAttribute("order") Order order,
                                     @RequestParam(value = "array_id_order", required = false) String[] array_id_order){
        ModelAndView mav = new ModelAndView();
        int status = OrderDao.Insert(order);
        if(status==1)
            mav.setViewName("redirect:/cashier");
        else
            mav.setViewName("redirect:/cashier"); //show error disini, redirect ke page 404 kek.
        return mav;
    }
}
