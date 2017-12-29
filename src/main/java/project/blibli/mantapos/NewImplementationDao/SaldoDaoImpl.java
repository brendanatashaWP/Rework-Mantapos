package project.blibli.mantapos.NewImplementationDao;

import project.blibli.mantapos.Model.SaldoAwal;
import project.blibli.mantapos.NewInterfaceDao.SaldoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaldoDaoImpl implements SaldoDao {

    private static final String tableSaldo = "saldo_awal";
    private static final String idResto = "id_resto";
    private static final String saldoAwal = "saldo_awal";
    private static final String tanggal = "tanggal";
    private static final String month = "month";
    private static final String year = "year";
    private static final String refTableResto = "restoran";

    @Override
    public int getSaldoAwal(int idResto, int monthh, int yearr) {
        int saldoAwal = 0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT " + this.saldoAwal + " FROM " + tableSaldo + " WHERE " + this.idResto + "=? AND " + month + "=? AND " + year + "=?"
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, monthh);
            preparedStatement.setInt(3, yearr);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                saldoAwal = resultSet.getInt(this.saldoAwal);
            }
        } catch (Exception ex){
            System.out.println("Gagal get saldo awal : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return saldoAwal;
    }

    @Override
    public int getSaldoAwalCustom(int idResto, int tanggal1, int month1, int year1) {
        int saldoAwalCustom=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT SUM (" + saldoAwal + ") FROM " + tableSaldo + " WHERE " + this.idResto + "=? " +
                            "AND " + tanggal + "=? AND " +
                            month + "=? AND " + year + "=?"
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, tanggal1);
            preparedStatement.setInt(3, month1);
            preparedStatement.setInt(4, year1);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                saldoAwalCustom = resultSet.getInt("sum");
            }
        } catch (Exception ex){
            System.out.println("Gagal get saldo awal custom : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return saldoAwalCustom;
    }

    @Override
    public void createTable() throws SQLException {
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + tableSaldo +
                            "(" +
                            idResto + " INT NOT NULL, " +
                            saldoAwal + " REAL NOT NULL, " +
                            tanggal + " INT NOT NULL, " +
                            month + " INT NOT NULL, " +
                            year + " INT NOT NULL, " +
                            "UNIQUE (" + month + "," + year + "), " +
                            "CONSTRAINT id_resto_fk FOREIGN KEY (" + idResto + ")" + "REFERENCES " + refTableResto + "(id))"
            );
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal create table " + tableSaldo + " : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public void insert(SaldoAwal modelData, Integer idResto) {
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + tableSaldo + "(" + this.idResto + "," + saldoAwal + "," + tanggal + "," + month + "," + year + ")" +
                            "VALUES(?,?,?,?,?)"
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, modelData.getSaldo_awal());
            preparedStatement.setInt(3, LocalDate.now().getDayOfMonth());
            preparedStatement.setInt(4, LocalDate.now().getMonthValue());
            preparedStatement.setInt(5, LocalDate.now().getYear());
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal insert saldo awal : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public List<SaldoAwal> readAll(Integer idResto, Integer itemPerPage, Integer page) {
        List<SaldoAwal> saldoAwalListTiapBulan = new ArrayList<>();
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT *" + " FROM " + tableSaldo + " WHERE " + this.idResto + "=? LIMIT ? OFFSET ?"
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, itemPerPage);
            preparedStatement.setInt(3, (page-1)*itemPerPage);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                SaldoAwal saldoAwal = new SaldoAwal();
                saldoAwal.setId_resto(resultSet.getInt(this.idResto));
                saldoAwal.setMonth(resultSet.getInt(month));
                saldoAwal.setYear(resultSet.getInt(year));
                saldoAwal.setSaldo_awal(resultSet.getInt(this.saldoAwal));
                saldoAwalListTiapBulan.add(saldoAwal);
            }
        } catch (Exception ex){
            System.out.println("Gagal read saldo awal tiap bulan : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return saldoAwalListTiapBulan;
    }

    @Override
    public SaldoAwal readOne(Integer idData) {
        return null;
    }

    @Override
    public int getLastId(Integer idResto) {
        return 0;
    }

    @Override
    public void update(SaldoAwal modelData, Integer idResto) {

    }

    @Override
    public void delete(Integer idData) {

    }

    @Override
    public int count(Integer idResto) {
        int count=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM " + tableSaldo + " WHERE " + this.idResto + "=?"
            );
            preparedStatement.setInt(1, idResto);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                count = resultSet.getInt(1);
            }
        } catch (Exception ex){
            System.out.println("Gagal get count saldo : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return count;
    }
}
