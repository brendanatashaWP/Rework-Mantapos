package project.blibli.mantapos.Controller.OwnerManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Model.User;
import project.blibli.mantapos.Service.OwnerManager.EmployeeService;

import java.sql.SQLException;

@RestController
public class EmployeeController {

    EmployeeService employeeService;

    @Autowired
    public EmployeeController (EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    //Jika user akses /employee, kurang lebih sama dengan menu/page dan outcome/page
    @GetMapping(value = "/employee", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView cashierListHtml(@RequestParam(value = "page", required = false) Integer page,
                                        Authentication authentication) throws SQLException {
        return employeeService.getMappingEmployee(authentication, page);
    }

    //Jika user menambahkan user baru
    @PostMapping(value = "/add-user", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addUserHtml(@ModelAttribute User user,
                                    Authentication authentication) throws SQLException {
        return employeeService.postMappingAddNewEmployee(authentication, user);
    }

    //Jika user menghapus user
    @GetMapping(value = "/delete/user", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView deleteCashier(@RequestParam(value = "id", required = false) Integer id,
                                      Authentication authentication) throws SQLException {
        return employeeService.postMappingDeleteEmployee(authentication, id);
    }
    //Jika user mengaktifkan lagi user
    @GetMapping(value = "/active/user", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView activeCashier(@RequestParam(value = "id", required = false) Integer id,
                                      Authentication authentication) throws SQLException {
        return employeeService.postMappingActivateEmployee(authentication, id);
    }
    //Jika user mengakses laman edit user (show form) beserta value value yang bersesuaian di input2 yang ada
    @GetMapping(value = "/edit/user")
    public ModelAndView editUserHtml(@RequestParam(value = "id", required = false) Integer id,
                                     Authentication authentication) throws SQLException {
        return employeeService.getMappingEditEmployee(authentication, id);
    }
    //Memposting data user yang baru
    @PostMapping(value = "/edit-user")
    public ModelAndView editUserPostHtml(@ModelAttribute User user,
                                         Authentication authentication) throws SQLException {
        return employeeService.postMappingEditEmployee(authentication, user);
    }

}
