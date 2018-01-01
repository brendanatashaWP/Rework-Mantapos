package project.blibli.mantapos.NewImplementationDao;

import project.blibli.mantapos.ImplementationDao.DbConnection;
import project.blibli.mantapos.Model.Pemasukkan;
import project.blibli.mantapos.Model.Pengeluaran;
import project.blibli.mantapos.NewInterfaceDao.PengeluaranDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PengeluaranDaoImpl implements PengeluaranDao {

    private static final String tablePengeluaran = "pengeluaran";
    private static final String id = "id";
    private static final String biaya = "biaya";
    private static final String dateCreated = "date_created";
    private static final String idResto = "id_resto";
    private static final String refTableResto = "restoran";

    private Pengeluaran pengeluaranMapping(ResultSet resultSet) throws SQLException {
        Pengeluaran pengeluaran = new Pengeluaran();
        pengeluaran.setIdResto(resultSet.getInt(idResto));
        pengeluaran.setBiaya(resultSet.getInt(biaya));
        pengeluaran.setDateCreated(resultSet.getTimestamp(dateCreated));
        return pengeluaran;
    }

    @Override
    public void createTable() throws SQLException {
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS " + tablePengeluaran + "(" +
                        id + " SERIAL PRIMARY KEY, " +
                        idResto + " INT NOT NULL, " +
                        biaya + " REAL NOT NULL, " +
                        dateCreated + " TIMESTAMP NOT NULL DEFAULT NOW()," +
                        "CONSTRAINT id_resto_fk FOREIGN KEY(" + idResto + ")" + " REFERENCES " + refTableResto + "(id))"
        );
        preparedStatement.executeUpdate();
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
    }

    @Override
    public void insert(Pengeluaran modelData) throws SQLException {
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO " + tablePengeluaran + "(" + idResto + "," + biaya + ")" +
                        "VALUES (?,?)"
        );
        preparedStatement.setInt(1, modelData.getIdResto());
        preparedStatement.setInt(2, modelData.getBiaya());
        preparedStatement.executeUpdate();
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
    }

    @Override
    public List<Pengeluaran> getAll(String condition) throws SQLException {
        List<Pengeluaran> pengeluaranList = new ArrayList<>();
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM " + tablePengeluaran + " WHERE " + condition
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            pengeluaranList.add(pengeluaranMapping(resultSet));
        }
        DbConnection.closeResultSet(resultSet);
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
        return pengeluaranList;
    }

    @Override
    public int getTotalPengeluaran(String condition) throws SQLException {
        int totalPengeluaran=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT SUM(" + biaya + ") FROM " + tablePengeluaran + " WHERE " + condition
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            totalPengeluaran = resultSet.getInt(1);
        }
        DbConnection.closeResultSet(resultSet);
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
        return totalPengeluaran;
    }

    @Override
    public Pengeluaran getOne(String condition) throws SQLException {
        Pengeluaran pengeluaran = new Pengeluaran();
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM " + tablePengeluaran + " WHERE " + condition
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            pengeluaran = pengeluaranMapping(resultSet);
        }
        DbConnection.closeResultSet(resultSet);
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
        return pengeluaran;
    }

    @Override
    public int getId(String condition) throws SQLException {
        return 0;
    }

    @Override
    public int count(String condition) throws SQLException {
        int count=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT COUNT(*) FROM " + tablePengeluaran + " WHERE " + condition
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            count = resultSet.getInt(1);
        }
        DbConnection.closeResultSet(resultSet);
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
        return count;
    }

    @Override
    public void update(Pengeluaran modelData, String condition) throws SQLException {

    }

    @Override
    public void deactivate(String condition) throws SQLException {

    }

    @Override
    public void activate(String condition) throws SQLException {

    }

}
