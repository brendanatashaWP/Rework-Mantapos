package project.blibli.mantapos.NewImplementationDao;

import project.blibli.mantapos.ImplementationDao.DbConnection;
import project.blibli.mantapos.Model.Pemasukkan;
import project.blibli.mantapos.NewInterfaceDao.PemasukkanDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PemasukkanDaoImpl implements PemasukkanDao {

    private static final String tablePemasukkan = "pemasukkan";
    private static final String id = "id";
    private static final String biaya = "biaya";
    private static final String dateCreated = "date_created";
    private static final String idResto = "id_resto";
    private static final String refTableResto = "restoran";

    private Pemasukkan pemasukkanMapping(ResultSet resultSet) throws SQLException {
        Pemasukkan pemasukkan = new Pemasukkan();
        pemasukkan.setIdResto(resultSet.getInt(idResto));
        pemasukkan.setBiaya(resultSet.getInt(biaya));
        pemasukkan.setDateCreated(resultSet.getTimestamp(dateCreated));
        return pemasukkan;
    }

    @Override
    public void createTable() throws SQLException {
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS " + tablePemasukkan + "(" +
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
    public void insert(Pemasukkan modelData) throws SQLException {
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO " + tablePemasukkan + "(" + idResto + "," + biaya + ")" +
                        "VALUES (?,?)"
        );
        preparedStatement.setInt(1, modelData.getIdResto());
        preparedStatement.setInt(2, modelData.getBiaya());
        preparedStatement.executeUpdate();
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
    }

    @Override
    public List<Pemasukkan> getAll(String condition) throws SQLException {
        List<Pemasukkan> pemasukkanList = new ArrayList<>();
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM " + tablePemasukkan + " WHERE " + condition
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            pemasukkanList.add(pemasukkanMapping(resultSet));
        }
        DbConnection.closeResultSet(resultSet);
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
        return pemasukkanList;
    }

    @Override
    public int getTotalPemasukkan(String condition) throws SQLException {
        int totalPemasukkan=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT SUM(" + biaya + ") FROM " + tablePemasukkan + " WHERE " + condition
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            totalPemasukkan = resultSet.getInt(1);
        }
        DbConnection.closeResultSet(resultSet);
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
        return totalPemasukkan;
    }

    @Override
    public Pemasukkan getOne(String condition) throws SQLException {
        Pemasukkan pemasukkan = new Pemasukkan();
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM " + tablePemasukkan + " WHERE " + condition
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            pemasukkan = pemasukkanMapping(resultSet);
        }
        DbConnection.closeResultSet(resultSet);
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
        return pemasukkan;
    }

    @Override
    public int getId(String condition) throws SQLException {
        //ini adalah get last Id
        int lastId=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT MAX(" + id + ") FROM " + tablePemasukkan + " WHERE " + condition
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            lastId = resultSet.getInt(1);
        }
        DbConnection.closeResultSet(resultSet);
        DbConnection.closePreparedStatement(preparedStatement);
        DbConnection.closeConnection(connection);
        return lastId;
    }

    @Override
    public int count(String condition) throws SQLException {
        int count=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT COUNT(*) FROM " + tablePemasukkan + " WHERE " + condition
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
    public void update(Pemasukkan modelData, String condition) throws SQLException {

    }

    @Override
    public void deactivate(String condition) throws SQLException {

    }

    @Override
    public void activate(String condition) throws SQLException {

    }
}
