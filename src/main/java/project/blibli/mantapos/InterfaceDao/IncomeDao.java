package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.Income;
import project.blibli.mantapos.Beans_Model.Restaurant;

import java.util.List;

public interface IncomeDao {
    void CreateTable();
    int Insert(int id_resto, int weekk, int monthh, int yearr, int income_amountt);
    int Update(int id_resto, int income_amountt);
    boolean isIncomeExists(int id_resto);
    int getIncomeAmount(int id_resto);
}
