package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.Income;

import java.util.List;

public interface IncomeDao {
    void CreateTable();
    int Insert(Income income);
    List<Integer> GetWeekly();
    List<Integer> GetMonthly();
    List<Integer> GetYearly();
}
