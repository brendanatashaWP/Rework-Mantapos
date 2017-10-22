package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.blibli.mantapos.Beans_Model.User;
import project.blibli.mantapos.ImplementationDao.UserDaoImpl;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    UserDaoImpl userDao = new UserDaoImpl();

    @PostMapping(value = "/add-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> addUserJson(@ModelAttribute("user")User user){
        Map<String, Object> param = new HashMap<>();
        int status = 0;
        String errorMsg=null;
        try{
            status = userDao.Insert(user);
        } catch (Exception ex){
            errorMsg=ex.toString();
        }
        if(status==1)
            param.put("inserted data", user);
        else{
            param.put("error", "failed insert into table users");
            param.put("message", errorMsg);
        }
        return param;
    }
}
