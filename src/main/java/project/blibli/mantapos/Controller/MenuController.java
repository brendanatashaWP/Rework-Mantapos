package project.blibli.mantapos.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.blibli.mantapos.Beans_Model.Menu;
import project.blibli.mantapos.ImplementationDao.MenuDaoImpl;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MenuController {

    MenuDaoImpl menuDao = new MenuDaoImpl();

    @PostMapping(value = "/add-menu")
    public Map<String, String> addMenuJson(@ModelAttribute("menu")Menu menu){
        Map<String, String> param = new HashMap<>();
        String errorMsg = null;
        int statusInsert = 0;
        try{
            statusInsert = menuDao.Insert(menu);
        } catch (Exception ex){
            errorMsg = ex.toString();
        }
        if(statusInsert==0){
            param.put("STATUS", "FAILED");
            param.put("MESSAGE", errorMsg);
        } else{
            param.put("STATUS", "SUCCESS");
        }
        return param;
    }

}
