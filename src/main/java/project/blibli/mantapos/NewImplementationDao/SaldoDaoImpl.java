package project.blibli.mantapos.NewImplementationDao;

import project.blibli.mantapos.Model.Saldo;
import project.blibli.mantapos.NewInterfaceDao.SaldoDao;

import java.sql.SQLException;
import java.util.List;

public class SaldoDaoImpl implements SaldoDao {
    @Override
    public void createTable() throws SQLException {

    }

    @Override
    public void insert(Saldo modelData) throws SQLException {

    }

    @Override
    public List<Saldo> getAll(String condition) throws SQLException {
        return null;
    }

    @Override
    public Saldo getOne(String condition) throws SQLException {
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
    public void update(Saldo modelData, String condition) throws SQLException {

    }

    @Override
    public void deactivate(String condition) throws SQLException {

    }

    @Override
    public void activate(String condition) throws SQLException {

    }
}
