package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Beans_Model.Restoran;
import project.blibli.mantapos.Beans_Model.User;
import project.blibli.mantapos.ImplementationDao.RestoranDaoImpl;
import project.blibli.mantapos.ImplementationDao.UserDaoImpl;

import java.util.List;

@RestController
public class AdminController {
    RestoranDaoImpl restoranDao = new RestoranDaoImpl();
    UserDaoImpl userDao = new UserDaoImpl();

    @GetMapping(value = "/restaurant", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView restaurantListHtml(){
        List<Restoran> restoranList = restoranDao.GetRestoranList();
        return new ModelAndView("admin-restaurant", "restoranList", restoranList);
    }
    @PostMapping(value = "/add-restaurant", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addRestaurantPost(@ModelAttribute("restoran") Restoran restoran,
                                          @ModelAttribute("user") User user){
        restoranDao.Insert(restoran);
        user.setRole("owner");
        user.setId_resto(restoranDao.GetRestoranIdBerdasarkanNamaResto(restoran.getNama_resto()));
        userDao.Insert(user, 1);
        return new ModelAndView("redirect:/restaurant");
    }
}
