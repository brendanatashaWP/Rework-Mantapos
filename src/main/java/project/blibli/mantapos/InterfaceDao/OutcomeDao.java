package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.Outcome;

public interface OutcomeDao {
    void CreateTable();
    int Insert(int id_restoo, Outcome outcome);
}
