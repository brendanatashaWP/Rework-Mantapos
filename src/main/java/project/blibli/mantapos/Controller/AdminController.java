package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Model.Restoran;
import project.blibli.mantapos.Model.User;
import project.blibli.mantapos.NewImplementationDao.RestoranDaoImpl;
import project.blibli.mantapos.NewImplementationDao.UserDaoImpl;

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
        List<Restoran> restoranList = restoranDao.readAllRestoran("owner"); //Mengambil list restoran yang terdaftar
        mav.addObject("restoranList", restoranList);
        double jumlahRestoran = restoranDao.countRestoran(); //Mengambil jumlah restoran yang terdaftar
        double jumlahPage = Math.ceil(jumlahRestoran/itemPerPage); //Menghitung jumlah page yang ada utk pagination. Misal jmlResto = 8 dan item per page = 5, maka akan ada 2 page dengan isi 5 dan 3 item
        List<Integer> pageList = new ArrayList<>();
        for (int i=1; i<=jumlahPage; i++){
            pageList.add(i); //menambahkan angka page (1, 2, 3, dst) ke list PageList, nanti di HTML angka2nya akan di plot ke pagination
        }
        mav.addObject("pageNo", page);
        mav.addObject("pageList", pageList);
        return mav;
    }
    @PostMapping(value = "/add-restaurant", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addRestaurantPost(@ModelAttribute("restoran") Restoran restoran,
                                          @ModelAttribute("user") User user){
        restoranDao.insert(restoran, 0); //melakukan insert object restoran ke database (isinya object ini adalah informasi restoran) (table restoran)
        user.setRole("owner"); //setRole untuk user yang di add sebagai owner
        user.setIdResto(restoranDao.readIdResto(restoran.getNamaResto())); //setIdResto berdasarkan nama restorannya. Jadi tadi kan sudah insert restoran, nama restorannya itu dijadikan parameter untuk mencari ID restoran tsb
        userDao.insert(user, user.getIdResto()); //insert object user ke database (table user dan user_roles --> yang menangani insert ke user_roles ada di method insert user)
        return new ModelAndView("redirect:/restaurant");
    }
}
