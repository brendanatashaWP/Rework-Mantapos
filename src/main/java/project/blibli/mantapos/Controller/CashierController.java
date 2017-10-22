package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Beans_Model.Income;
import project.blibli.mantapos.Beans_Model.Menu;
import project.blibli.mantapos.Beans_Model.Order;
import project.blibli.mantapos.Beans_Model.Restaurant;
import project.blibli.mantapos.ImplementationDao.MenuDaoImpl;
import project.blibli.mantapos.ImplementationDao.OrderDaoImpl;
import project.blibli.mantapos.ImplementationDao.OrderedMenuDaoImpl;
import project.blibli.mantapos.ImplementationDao.RestaurantDaoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CashierController {

    MenuDaoImpl menuDao = new MenuDaoImpl();
    OrderDaoImpl orderDao = new OrderDaoImpl();
    OrderedMenuDaoImpl orderedMenuDao = new OrderedMenuDaoImpl();
    RestaurantDaoImpl restaurantDao = new RestaurantDaoImpl();

    @GetMapping(value = "/cashier", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView cashierHtml(Authentication authentication){
        List<Menu> menuList = menuDao.getAllMenu();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("cashier");
        mav.addObject("menuList", menuList);

        //Ambil username yang sedang login, untuk nantinya diambil ID restaurant-nya
        String loggedInUsername = authentication.getName();
        mav.addObject("loggedInUsername", loggedInUsername);

        Restaurant restaurant = restaurantDao.GetRestaurantInfo(loggedInUsername);
        mav.addObject("restaurant", restaurant);
        return mav;
    }

    @GetMapping(value = "/cashier", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Menu>> cashierJson(){
        Map<String, List<Menu>> param = new HashMap<>();
        List<Menu> menuList = menuDao.getAllMenu();
        param.put("menu", menuList);
        return param;
    }

    @PostMapping(value = "/add-order", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> addOrderJson(@ModelAttribute("order") Order order){
        Map<String, String> param = new HashMap<>();
        int status = 0;
        String errorMsg = null;
        try{
            status = orderDao.Insert(order);
            Income income = new Income();
        } catch (Exception ex){
            errorMsg = ex.toString();
        }
        if (status==1){
            param.put("status", "success!");
            param.put("customer name", order.getCustomer_name());
            param.put("table no", String.valueOf(order.getTable_no()));
            param.put("price total", String.valueOf(order.getPrice_total()));
            param.put("notes", order.getNotes());
            //order time, week, month, dan year return NULL
            //karena kan di model order itu kosong value mereka, value mereka di assign
            //di OrderDao tanpa harus ke model
            //atau mending assign waktu order nya disini aja daripada di DAO?
            param.put("order time", order.getOrdered_time());
            param.put("week", String.valueOf(order.getWeek()));
            param.put("month", String.valueOf(order.getMonth()));
            param.put("year", String.valueOf(order.getYear()));
        } else{
            param.put("status", "failed!");
            param.put("message", errorMsg);
        }
        return param;
    }

    @PostMapping(value = "/add-order", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addOrderHtml(@ModelAttribute("order") Order order,
                                     @RequestParam(value = "array_id_order", required = false) String[] array_id_order,
                                     @RequestParam(value = "array_qty", required = false) String[] array_qty){
        ModelAndView mav = new ModelAndView();
        int statusOrder = orderDao.Insert(order);
        int lastOrderId = orderDao.getLastOrderId();
        int statusOrderedMenu = 0;
        for(int i=0; i<array_id_order.length; i++){
            statusOrderedMenu = orderedMenuDao.Insert(lastOrderId, Integer.parseInt(array_id_order[i]),
                    Integer.parseInt(array_qty[i]));
        }
        if(statusOrder==1 && statusOrderedMenu==1)
            mav.setViewName("redirect:/cashier");
        else
            mav.setViewName("redirect:/cashier"); //show error disini, redirect ke page 404 kek.
        return mav;
    }

//    @PostMapping(value = "/receipt", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Map<String, String> receiptPostJson(@ModelAttribute("restaurant")Restaurant restaurant,
//                                           @ModelAttribute("receipt") Receipt receipt,
//                                           @ModelAttribute("order")Order order){
//        Map<String, String> param = new HashMap<>();
//        param.put("restaurant_name", restaurant.getRestaurant_name());
//        param.put("restaurant_address", restaurant.getRestaurant_address());
//        param.put("customer_name", order.getCustomer_name());
//        param.put("price_total", String.valueOf(order.getPrice_total()));
//        param.put("cash_paid", String.valueOf(receipt.getCash_paid()));
//        param.put("cash_change", String.valueOf(receipt.getCash_change()));
//        return param;
//    }
//
//    @PostMapping(value = "/receipt", produces = MediaType.TEXT_HTML_VALUE)
//    public ModelAndView receiptPostHtml(@ModelAttribute("restaurant") Restaurant restaurant,
//                                        @ModelAttribute("receipt") Receipt receipt,
//                                        @ModelAttribute("order") Order order){
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("restaurant_name", restaurant.getRestaurant_name());
//        mav.addObject("restaurant_address", restaurant.getRestaurant_address());
//        mav.addObject("customer_name", order.getCustomer_name());
//        mav.addObject("price_total", String.valueOf(order.getPrice_total()));
//        mav.addObject("cash_paid", String.valueOf(receipt.getCash_paid()));
//        mav.addObject("cash_change", String.valueOf(receipt.getCash_change()));
//        mav.setViewName("receipt");
//        return mav;
//    }
//
//    @GetMapping(value = "/receipt", produces = MediaType.TEXT_HTML_VALUE)
//    public ModelAndView receiptGetHtml(){
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("receipt");
//        return mav;
//    }
}
