package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.Income;
import project.blibli.mantapos.Beans_Model.Restaurant;

import java.util.List;

public interface IncomeDao {
    void CreateTable();
    int Insert(Restaurant restaurant, Income income);
}
