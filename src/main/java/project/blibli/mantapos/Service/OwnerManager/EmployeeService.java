package project.blibli.mantapos.Service.OwnerManager;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Helper.GetIdResto;
import project.blibli.mantapos.ImplementationDao.UserDaoImpl;
import project.blibli.mantapos.Model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    UserDaoImpl userDao = new UserDaoImpl();
    int itemPerPage=5;

    public ModelAndView getMappingEmployee(Authentication authentication,
                                           Integer page){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("owner-manager/employee");
        String role = getLoggedInUserRole(authentication);
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(authentication.getName());
        if(page == null){
            page=1;
        }
        List<User> userList = getAllUser(authentication, idResto, page);
        double jumlahEmployee = getCountUser(idResto);
        double jumlahPage = Math.ceil(jumlahEmployee/itemPerPage);
        List<Integer> pageList = new ArrayList<>();
        for (int i=1; i<=jumlahPage; i++){
            pageList.add(i);
        }
        mav.addObject("pageNo", page);
        mav.addObject("pageList", pageList);
        mav.addObject("userList", userList);
        mav.addObject("role", role);
        return mav;
    }

    public ModelAndView postMappingAddNewEmployee(Authentication authentication,
                                                  User user){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/employee");
        if (getLoggedInUserRole(authentication).equals("[manager]")){
            user.setRole("cashier");
        }
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(authentication.getName());
        user.setIdResto(idResto);
        insertNewUser(idResto, user);
        return mav;
    }

    public ModelAndView getMappingEditEmployee(Authentication authentication,
                                               Integer idUser){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("owner-manager/edit-user");
        mav.addObject("role", getLoggedInUserRole(authentication));
        mav.addObject("userObject", getDetailUserById(idUser));
        return mav;
    }

    public ModelAndView postMappingEditEmployee(Authentication authentication,
                                                User user){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/employee");
        int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(authentication.getName());
        updateUser(user, idResto);
        return mav;
    }

    public ModelAndView postMappingDeleteEmployee(Authentication authentication,
                                                  Integer idUser){
        ModelAndView mav = new ModelAndView();
        if(idUser==null){
            mav.setViewName("redirect:/employee");
        } else{
            deleteUser(authentication,idUser);
            if(getLoggedInUserRole(authentication).equals("[admin]")){
                mav.setViewName("redirect:/restaurant");
            } else{
                mav.setViewName("redirect:/employee");
            }
        }
        return mav;
    }

    public ModelAndView postMappingActivateEmployee(Authentication authentication,
                                                    Integer idUser){
        ModelAndView mav = new ModelAndView();
        if(idUser==null){
            mav.setViewName("redirect:/employee");
        } else{
            activateUser(idUser);
            if(getLoggedInUserRole(authentication).equals("[admin]")){
                mav.setViewName("redirect:/restaurant");
            } else {
                mav.setViewName("redirect:/employee");
            }
        }
        return mav;
    }

    private void insertNewUser(int idResto,
                              User user){
        userDao.insert(user, idResto);
    }

    private List<User> getAllUser(Authentication authentication,
                                  int idResto,
                                  int page){
        List<User> userList;
        if (getLoggedInUserRole(authentication).equals("[manager]")) {
            userList = userDao.readAllUsers(idResto, "cashier", itemPerPage, page);
        } else{
            userList = userDao.readAllUsers(idResto, "manager&cashier", itemPerPage, page);
        }
        return userList;
    }

    private User getDetailUserById(int idUser){
        User user = userDao.readOne(idUser);
        return user;
    }

    private void updateUser(User user,
                            int idResto){
        userDao.update(user, idResto);
    }

    private void deleteUser(Authentication authentication,
                            int idUser){
        if(getLoggedInUserRole(authentication).equals("[owner]")) {
            int idResto = GetIdResto.getIdRestoBasedOnUsernameTerkait(authentication.getName());
            userDao.deleteUserAndDependencies(idResto);
        } else {
            userDao.delete(idUser);
        }
    }

    private void activateUser(int idUser){
        userDao.activateUser(idUser);
    }

    private String getLoggedInUserRole(Authentication authentication){
        String role = authentication.getAuthorities().toString();
        return role;
    }

    private int getCountUser(int idResto){
        int countUser = userDao.count(idResto);
        return countUser;
    }
}
