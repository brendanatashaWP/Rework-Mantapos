package project.blibli.mantapos.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.blibli.mantapos.Beans_Model.User;
import project.blibli.mantapos.Dao.UserDao;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @PostMapping(value = "/add-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> addUserJson(@ModelAttribute("user")User user){
        Map<String, Object> param = new HashMap<>();
        int status = UserDao.Insert(user);
        if(status==1)
            param.put("inserted data", user);
        else
            param.put("error", "failed insert into table users");
        return param;
    }
}
