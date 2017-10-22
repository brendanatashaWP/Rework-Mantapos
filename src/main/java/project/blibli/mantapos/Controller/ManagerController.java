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

    @GetMapping(value = "/income/{scale}")
    public Map<String, List<Integer>> incomeJson(@PathVariable("scale") String scale){
        Map<String, List<Integer>> param = new HashMap<>();
        List<Integer> incomeList = new ArrayList<>();
        if(scale.toUpperCase().equals("WEEKLY")){
            incomeList = getWeeklyIncome();
        } else if(scale.toUpperCase().equals("MONTHLY")){
            incomeList = getMonthlyIncome();
        } else if(scale.toUpperCase().equals("YEARLY")){
            incomeList = getYearlyIncome();
        }
        param.put("RESULT", incomeList);
        return param;
    }

    private List<Integer> getWeeklyIncome(){
        List<Integer> incomeList = incomeDao.GetWeekly();
        return incomeList;
    }
    private List<Integer> getMonthlyIncome(){
        List<Integer> incomeList = incomeDao.GetMonthly();
        return incomeList;
    }
    private List<Integer> getYearlyIncome(){
        List<Integer> incomeList = incomeDao.GetYearly();
        return incomeList;
    }
}
