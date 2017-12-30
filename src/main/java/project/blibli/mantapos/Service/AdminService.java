package project.blibli.mantapos.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Helper.GetIdResto;
import project.blibli.mantapos.ImplementationDao.RestoranDaoImpl;
import project.blibli.mantapos.ImplementationDao.UserDaoImpl;
import project.blibli.mantapos.Model.Restoran;
import project.blibli.mantapos.Model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    RestoranDaoImpl restoranDao = new RestoranDaoImpl();
    UserDaoImpl userDao = new UserDaoImpl();

    int itemPerPage=5;

    public ModelAndView getMappingRestoran(Integer page){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin-restaurant");
        mav.addObject("restoranList", getAllRegisteredRestoran());
        if (page==null){
            page=1;
        }
        double jumlahRestoran = getCountRestoran();
        double jumlahPage = Math.ceil(jumlahRestoran/itemPerPage);
        System.out.println("jumlah resto : " + jumlahRestoran);
        List<Integer> pageList = new ArrayList<>();
        for (int i=1; i<=jumlahPage; i++){
            pageList.add(i); //menambahkan angka page (1, 2, 3, dst) ke list PageList, nanti di HTML angka2nya akan di plot ke pagination
        }
        mav.addObject("pageNo", page);
        mav.addObject("pageList", pageList);
        return mav;
    }

    public ModelAndView postMappingRestoran(Restoran restoran,
                                            User user){
        ModelAndView mav = new ModelAndView();
        insertNewRestoran(restoran, 0);
        int idResto = GetIdResto.getIdRestoBasedOnNamaResto(restoran.getNamaResto());
        user.setRole("owner");
        user.setIdResto(idResto);
        insertNewOwner(user, idResto);
        mav.setViewName("redirect:/restaurant");
        return mav;
    }

    public List<Restoran> getAllRegisteredRestoran(){
        List<Restoran> restoranList = restoranDao.readAllRestoran("owner");
        return restoranList;
    }

    public int getCountRestoran(){
        int countRestoran = restoranDao.countRestoran();
        return countRestoran;
    }

    public void insertNewRestoran(Restoran restoran, int idResto){
        restoranDao.insert(restoran, idResto);
    }

    public void insertNewOwner(User user, int idResto){
        userDao.insert(user, idResto);
    }

}
