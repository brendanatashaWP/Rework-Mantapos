package project.blibli.mantapos.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.blibli.mantapos.Beans_Model.Income;
import project.blibli.mantapos.ImplementationDao.IncomeDaoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ManagerController {

    private IncomeDaoImpl incomeDao = new IncomeDaoImpl();

    @GetMapping(value = "/ledger/daily/{month}")
    public Map<String, List<Integer>> incomeJson(@PathVariable("month") String month){
        Map<String, List<Integer>> param = new HashMap<>();
        List<Integer> incomeList = new ArrayList<>();
        param.put("RESULT", incomeList);
        return param;
    }
}
