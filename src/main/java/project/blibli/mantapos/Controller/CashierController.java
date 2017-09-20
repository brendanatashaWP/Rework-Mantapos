package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CashierController {

    @GetMapping(value = "/cashier", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView cashierHtml(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("cashier");
        return mav;
    }
}
