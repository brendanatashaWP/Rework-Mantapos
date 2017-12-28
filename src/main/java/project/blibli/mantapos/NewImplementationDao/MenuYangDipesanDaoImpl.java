package project.blibli.mantapos.NewImplementationDao;

import project.blibli.mantapos.NewInterfaceDao.MenuYangDipesanDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MenuYangDipesanDaoImpl implements MenuYangDipesanDao {

    private static final String tableMenuYangDipesan = "menu_yang_dipesan";
    private static final String idOrder = "id_order";
    private static final String idMenu = "id_menu";
    private static final String qty = "quantity";
    private static final String refTableOrder = "ledger_harian";
    private static final String refTableMenu = "menu";

    @Override
    public void createTable() throws SQLException {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + tableMenuYangDipesan +
                            "(" +
                            idOrder + " INT NOT NULL, " +
                            idMenu + " INT NOT NULL, " +
                            qty + " INT NOT NULL, " +
                            "CONSTRAINT id_order_fk FOREIGN KEY (" + idOrder + ") REFERENCES " + refTableOrder + "(id)," +
                            "CONSTRAINT id_menu_fk FOREIGN KEY (" + idMenu + ") REFERENCES " + refTableMenu + "(id))"
            );
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal create table " + tableMenuYangDipesan + " : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    //Di-replace dengan insertMenuYangDipesan karena parameter yang dibutuhkan adalah idOrder, idMenu, dan qty
    @Override
    public void insert(Void modelData, Integer idResto) {

    }

    @Override
    public List<Void> readAll(Integer idResto, Integer itemPerPage, Integer page) {
        return null;
    }

    @Override
    public Void readOne(Integer idData) {
        return null;
    }

    @Override
    public int getLastId(Integer idResto) {
        return 0;
    }

    @Override
    public void update(Void modelData, Integer idResto) {

    }

    @Override
    public void delete(Integer idData) {

    }

    @Override
    public int count(Integer idResto) {
        return 0;
    }

    @Override
    public void insertMenuYangDipesan(int idOrder, int idMenu, int qty) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + tableMenuYangDipesan +
                            "(" +
                            this.idOrder + "," +
                            this.idMenu + "," +
                            this.qty + ")" +
                            " VALUES (?,?,?)"
            );
            preparedStatement.setInt(1, idOrder);
            preparedStatement.setInt(2, idMenu);
            preparedStatement.setInt(3, qty);
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal insert menu yang dipesan : " +  ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }
}
