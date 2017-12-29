package project.blibli.mantapos.ImplementationDao;

import project.blibli.mantapos.Model.Saldo;
import project.blibli.mantapos.InterfaceDao.SaldoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaldoDaoImpl implements SaldoDao {

    private static final String tableSaldoAwal = "saldo_awal";
    private static final String tableSaldoAkhir = "saldo_akhir";
    private static final String idResto = "id_resto";
    private static final String saldoAwal = "saldo_awal";
    private static final String saldoAkhir = "saldo_akhir";
    private static final String month = "month";
    private static final String year = "year";
    private static final String refTableResto = "restoran";

    //get Saldo Awal tidak perlu parameter month ataupun year, langsung ambil aja saldo awal yang sesuai
    //dengan id resto karena saldo awal hanya 1 record saja
    @Override
    public int getSaldoAwal(int idResto) {
        int saldoAwal = 0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT " + this.saldoAwal + " FROM " + tableSaldoAwal + " WHERE " + this.idResto + "=?"
            );
            preparedStatement.setInt(1, idResto);
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

    //saldo awal custom, ambil saldo akhir dari bulan x-1 dari bulan x-y.
    //misal custom range antara bulan maret-april, berarti saldo awalnya adalah saldo akhir bulan februari
    @Override
    public int getSaldoAwalCustom(int idResto, int tanggal1, int month1, int year1) {
        int saldoAwalCustom=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT SUM (" + saldoAwal + ") FROM " + tableSaldoAwal + " WHERE " + this.idResto + "=? " +
                            "AND " +
                            month + "=? AND " + year + "=?"
            );
            preparedStatement.setInt(1, idResto);
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
    public int isSaldoAkhirExists(int idResto, int month, int year) {
        int count=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM " + tableSaldoAkhir + " WHERE " + this.idResto + "=? AND " + this.month + "=? AND " + this.year + "=?"
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, month);
            preparedStatement.setInt(3, year);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (Exception ex){
            System.out.println("Gagal get count saldo akhir : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return count;
    }

    @Override
    public int getSaldoAkhir(int idResto, int month, int year) {
        int saldoAkhir=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT " + this.saldoAkhir + " FROM " + tableSaldoAkhir + " WHERE " + this.idResto + "=?" +
                            " AND " + this.month + "=? AND " + this.year + "=?"
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, month);
            preparedStatement.setInt(3, year);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                saldoAkhir = resultSet.getInt(1);
            }
        } catch (Exception ex){
            System.out.println("Gagal get saldo akhir : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return saldoAkhir;
    }

    @Override
    public void createTable() throws SQLException {
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + tableSaldoAwal +
                            "(" +
                            idResto + " INT NOT NULL, " +
                            saldoAwal + " REAL NOT NULL, " +
                            month + " INT NOT NULL, " +
                            year + " INT NOT NULL, " +
                            "UNIQUE (" + month + "," + year + "), " +
                            "CONSTRAINT id_resto_fk FOREIGN KEY (" + idResto + ")" + "REFERENCES " + refTableResto + "(id))"
            );
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + tableSaldoAkhir +
                            "(" +
                            idResto + " INT NOT NULL, " +
                            saldoAkhir + " REAL NOT NULL, " +
                            month + " INT NOT NULL, " +
                            year + " INT NOT NULL, " +
                            "CONSTRAINT id_resto_fk FOREIGN KEY (" + idResto + ")" + "REFERENCES " + refTableResto + "(id))"
            );
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal create table " + tableSaldoAwal + " atau " + tableSaldoAkhir + " : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public void insert(Saldo modelData, Integer idResto) {
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        try{
            if(modelData.getTipe_saldo().equals("awal")) {
                //cek apakah saldo sudah ada sebelumnya, karena saldo bolehnya hanya diisi sekali saja!
                if (count(idResto) == 0) {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO " + tableSaldoAwal + "(" + this.idResto + "," + saldoAwal + "," + month + "," + year + ")" +
                                    "VALUES(?,?,?,?)"
                    );
                    preparedStatement.setInt(1, idResto);
                    preparedStatement.setInt(2, modelData.getSaldo());
                    preparedStatement.setInt(3, modelData.getMonth());
                    preparedStatement.setInt(4, modelData.getYear());
                }
            } else {
                //saldo akhir belum ada untuk bulan dan tahun itu, maka insert saldo akhir baru
                if(isSaldoAkhirExists(idResto, modelData.getMonth(), modelData.getYear())==0){
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO " + tableSaldoAkhir + "(" + this.idResto + "," + saldoAkhir + "," + month + "," + year + ")" +
                                    "VALUES(?,?,?,?)"
                    );
                    preparedStatement.setInt(1, idResto);
                    preparedStatement.setInt(2, modelData.getSaldo());
                    preparedStatement.setInt(3, modelData.getMonth());
                    preparedStatement.setInt(4, modelData.getYear());
                } else{ //saldo akhir sudah ada untuk bulan dan tahun itu maka update saja //TODO : panggil method update di bawah aja ini nanti dipindah ya
                    preparedStatement = connection.prepareStatement(
                            "UPDATE " + tableSaldoAkhir + " SET " + saldoAkhir + "=? WHERE " + this.idResto + "=?  AND " + month + "=? AND " + year + "=?"
                    );
                    preparedStatement.setInt(1, modelData.getSaldo());
                    preparedStatement.setInt(2, idResto);
                    preparedStatement.setInt(3, modelData.getMonth());
                    preparedStatement.setInt(4, modelData.getYear());
                }
            }
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal insert saldo awal : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public List<Saldo> readAll(Integer idResto, Integer itemPerPage, Integer page) {
        List<Saldo> saldoListTiapBulan = new ArrayList<>();
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT *" + " FROM " + tableSaldoAwal + " WHERE " + this.idResto + "=? LIMIT ? OFFSET ?"
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, itemPerPage);
            preparedStatement.setInt(3, (page-1)*itemPerPage);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Saldo saldo = new Saldo();
                saldo.setId_resto(resultSet.getInt(this.idResto));
                saldo.setMonth(resultSet.getInt(month));
                saldo.setYear(resultSet.getInt(year));
                saldo.setSaldo(resultSet.getInt(this.saldoAwal));
                saldoListTiapBulan.add(saldo);
            }
        } catch (Exception ex){
            System.out.println("Gagal read saldo awal tiap bulan : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return saldoListTiapBulan;
    }

    @Override
    public Saldo readOne(Integer idData) {
        return null;
    }

    @Override
    public int getLastId(Integer idResto) {
        return 0;
    }

    @Override
    public void update(Saldo modelData, Integer idResto) {

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
                    "SELECT COUNT(*) FROM " + tableSaldoAwal + " WHERE " + this.idResto + "=?"
            );
            preparedStatement.setInt(1, idResto);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (Exception ex){
            System.out.println("Gagal get count saldo awal : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return count;
    }
}
