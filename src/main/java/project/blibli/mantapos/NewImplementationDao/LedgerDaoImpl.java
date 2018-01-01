package project.blibli.mantapos.NewImplementationDao;

import project.blibli.mantapos.ImplementationDao.DbConnection;
import project.blibli.mantapos.Model.Ledger;
import project.blibli.mantapos.NewInterfaceDao.LedgerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LedgerDaoImpl implements LedgerDao {

    private static final String tableLedger = "ledger_harian";
    private static final String idLedger = "id";
    private static final String tipe = "tipe";
    private static final String keperluan = "keperluan";
    private static final String dateCreated = "date_created";
    private static final String biaya = "biaya";
    private static final String idResto = "id_resto";
    private static final String refTableResto = "restoran";

    private static final String tipeLedger = "tipe_ledger";
    private static final String tipeDebit = "debit";
    private static final String tipeKredit = "kredit";

    @Override
    public void createTable() throws SQLException {
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS " + tableLedger +
                        "(" +
                        idLedger + " SERIAL PRIMARY KEY, " +
                        idResto + " INT NOT NULL, " +
                        tipe + " " + tipeLedger + " NOT NULL, " +
                        biaya + " INT NOT NULL, " +
                        keperluan + " TEXT NOT NULL, " +
                        dateCreated + " TIMESTAMP NOT NULL DEFAULT NOW(), " +
                        "CONSTRAINT id_resto_fk FOREIGN KEY(" + idResto + ")" + " REFERENCES " + refTableResto + "(id))"
        );
        preparedStatement.executeUpdate();
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
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
