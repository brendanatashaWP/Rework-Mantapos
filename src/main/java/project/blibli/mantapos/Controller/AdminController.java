package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Model.Restoran;
import project.blibli.mantapos.Model.User;
import project.blibli.mantapos.ImplementationDao.RestoranDaoImpl;
import project.blibli.mantapos.ImplementationDao.UserDaoImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {
    RestoranDaoImpl restoranDao = new RestoranDaoImpl();
    UserDaoImpl userDao = new UserDaoImpl();
    int itemPerPage=5;

    @GetMapping(value = "/restaurant/{page}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView restaurantListHtml(@PathVariable("page") int page){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin-restaurant");
        List<Restoran> restoranList = restoranDao.GetRestoranList(itemPerPage, page);
        mav.addObject("restoranList", restoranList);
        double jumlahRestoran = restoranDao.jumlahRestoran();
        double jumlahPage = Math.ceil(jumlahRestoran/itemPerPage);
        List<Integer> pageList = new ArrayList<>();
        for (int i=1; i<=jumlahPage; i++){
            pageList.add(i);
        }
        mav.addObject("pageNo", page);
        mav.addObject("pageList", pageList);
        return mav;
    }
    @PostMapping(value = "/add-restaurant", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addRestaurantPost(@ModelAttribute("restoran") Restoran restoran,
                                          @ModelAttribute("user") User user){
        restoranDao.Insert(restoran);
        user.setRole("owner");
        user.setId_resto(restoranDao.GetRestoranIdBerdasarkanNamaResto(restoran.getNama_resto()));
        userDao.Insert(user);
        return new ModelAndView("redirect:/restaurant");
    }
}
