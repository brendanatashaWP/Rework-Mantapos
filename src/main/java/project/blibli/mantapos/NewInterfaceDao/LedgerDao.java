package project.blibli.mantapos.NewInterfaceDao;

import project.blibli.mantapos.Model.Ledger;

import java.sql.SQLException;
import java.util.List;

public interface LedgerDao extends DaoInterface<Ledger, String> {

    void createTipeLedger() throws SQLException;
    int getTotal(String condition) throws SQLException;
    List<Ledger> getAllBulanan(String condition) throws SQLException;
    List<Ledger> getAllTahunan(String condition) throws SQLException;
    List<String> getAllBulananUntukDashboard(String condition) throws SQLException;

}
