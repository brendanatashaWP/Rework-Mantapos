package project.blibli.mantapos.NewImplementationDao;

import project.blibli.mantapos.Model.Ledger;
import project.blibli.mantapos.NewInterfaceDao.LedgerDao;

import java.sql.SQLException;
import java.util.List;

public class LedgerDaoImpl implements LedgerDao {
    @Override
    public void createTable() throws SQLException {
        
    }

    @Override
    public void insert(Ledger modelData) throws SQLException {

    }

    @Override
    public List<Ledger> getAll(String condition) throws SQLException {
        return null;
    }

    @Override
    public Ledger getOne(String condition) throws SQLException {
        return null;
    }

    @Override
    public int getId(String condition) throws SQLException {
        return 0;
    }

    @Override
    public int count(String condition) throws SQLException {
        return 0;
    }

    @Override
    public void update(Ledger modelData, String condition) throws SQLException {

    }

    @Override
    public void deactivate(String condition) throws SQLException {

    }

    @Override
    public void activate(String condition) throws SQLException {

    }
}
