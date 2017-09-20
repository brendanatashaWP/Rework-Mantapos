package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Beans_Model.Menu;
import project.blibli.mantapos.Dao.MenuDao;

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
}
