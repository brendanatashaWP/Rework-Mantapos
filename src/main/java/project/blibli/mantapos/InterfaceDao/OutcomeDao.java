package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.Outcome;

import java.util.List;

public interface OutcomeDao {
    void CreateTable();
    int Insert(int id_restoo, Outcome outcome);
    List<Outcome> getOutcome(int id_restoo);
}
