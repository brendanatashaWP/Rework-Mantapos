package project.blibli.mantapos.NewImplementationDao;

import project.blibli.mantapos.Model.Ledger;
import project.blibli.mantapos.MonthNameGenerator;
import project.blibli.mantapos.NewInterfaceDao.LedgerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LedgerDaoImpl implements LedgerDao {

    private static final String tableLedger = "ledger_harian";
    private static final String idLedger = "id";
    private static final String tipe = "tipe";
    private static final String keperluan = "keperluan";
    private static final String biaya = "biaya";
    private static final String waktu = "waktu";
    private static final String tanggal = "tanggal";
    private static final String week = "week";
    private static final String month = "month";
    private static final String year = "year";
    private static final String idResto = "id_resto";
    private static final String refTableResto = "restoran";

    private static final String tipeLedger = "tipe_ledger";
    private static final String tipeDebit = "debit";
    private static final String tipeKredit = "kredit";

    Ledger ledger = new Ledger();

    @Override
    public void createTipeLedger() {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "CREATE TYPE " + tipeLedger + " AS ENUM " + "(" +
                            "'" + tipeDebit + "'," +
                            "'" + tipeKredit + "'" +
                            ")"
            );
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal create tipe ledger : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public List<Ledger> getListBulanDanTahun(int idResto) {
        List<Ledger> listBulanDanTahun = new ArrayList<>();
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT " + month + "," + year + " FROM " + tableLedger +
                            " WHERE " + this.idResto + "=?" +
                            " GROUP BY " + month + "," + year +
                            " ORDER BY " + month + "," + year + " ASC"
            );
            preparedStatement.setInt(1, idResto);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ledger.setMonth(resultSet.getInt(month));
                ledger.setYear(resultSet.getInt(year));
                //Keperluan di bawah ini bukanlah keperluan, tapi untuk String nama bulan (alih fungsi)
                ledger.setKeperluan(MonthNameGenerator.MonthNameGenerator(resultSet.getInt(month)));
                listBulanDanTahun.add(ledger);
            }
        } catch (Exception ex){
            System.out.println("Gagal get list bulan dan tahun : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return listBulanDanTahun;
    }

    @Override
    public List<Ledger> getLedgerHarian(int idResto, int monthh, int yearr) {
        List<Ledger> ledgerListHarian = new ArrayList<>();
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT *" + " FROM " + tableLedger +
                            " WHERE " + this.idResto + "=? AND " + month + "=? AND " + year + "=?" +
                            " ORDER BY " + waktu + " ASC"
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, monthh);
            preparedStatement.setInt(3, yearr);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ledger.setKeperluan(resultSet.getString(keperluan));
                ledger.setTipe(resultSet.getString(tipe));
                ledger.setBiaya(resultSet.getDouble(biaya));
                ledger.setWaktu(resultSet.getString(waktu));
                ledgerListHarian.add(ledger);
            }
        } catch (Exception ex){
            System.out.println("Gagal get ledger harian : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return ledgerListHarian;
    }

    @Override
    public List<Ledger> getLedgerMingguan(int idResto, int monthh, int yearr) {
        List<Ledger> ledgerListMingguan = new ArrayList<>();
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT " + week + "," + tipe + ", SUM(" + biaya + ") FROM " + tableLedger +
                            " WHERE " + this.idResto + "=? AND " + month + "=? AND " + year + "=?" +
                            " GROUP BY " + tipe + "," + week +
                            " ORDER BY " + week + "," + tipe + " ASC"
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, monthh);
            preparedStatement.setInt(3, yearr);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ledger.setWeek(resultSet.getInt(week));
                ledger.setTipe(resultSet.getString(tipe));
                ledger.setBiaya(resultSet.getDouble("sum"));
                ledgerListMingguan.add(ledger);
            }
        } catch (Exception ex){
            System.out.println("Gagal get ledger mingguan : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return ledgerListMingguan;
    }

    @Override
    public List<Ledger> getLedgerBulanan(int idResto, int yearr) {
        List<Ledger> ledgerListBulanan = new ArrayList<>();
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT " + month + "," + tipe + ", SUM(" + biaya + ") FROM " + tableLedger +
                            " WHERE " + this.idResto + "=? AND " + year + "=?" +
                            " GROUP BY " + month + "," + tipe +
                            " ORDER BY " + month + "," + tipe + " ASC"
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, yearr);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ledger.setMonth(resultSet.getInt(month));
                ledger.setTipe(resultSet.getString(tipe));
                ledger.setBiaya(resultSet.getDouble("sum"));
                ledgerListBulanan.add(ledger);
            }
        } catch (Exception ex){
            System.out.println("Gagal get ledger bulanan : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return ledgerListBulanan;
    }

    @Override
    public List<Ledger> getLedgerCustom(int idResto, int tanggal1, int month1, int year1, int tanggal2, int month2, int year2) {
        List<Ledger> ledgerListCustom = new ArrayList<>();
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT *" + " FROM " + tableLedger +
                            " WHERE " + this.idResto + "=? AND " + tanggal + " BETWEEN ? AND ? AND " + month + " BETWEEN ? AND ? AND " + year + " BETWEEN ? AND ?" +
                            " ORDER BY " + waktu + " ASC"
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, tanggal1);
            preparedStatement.setInt(3, tanggal2);
            preparedStatement.setInt(4, month1);
            preparedStatement.setInt(5, month2);
            preparedStatement.setInt(6, year1);
            preparedStatement.setInt(7, year2);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ledger.setKeperluan(resultSet.getString(keperluan));
                ledger.setTipe(resultSet.getString(tipe));
                ledger.setBiaya(resultSet.getDouble(biaya));
                ledger.setWaktu(resultSet.getString(waktu));
                ledgerListCustom.add(ledger);
            }
        } catch (Exception ex){
            System.out.println("Gagal get ledger custom : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeResultSet(resultSet);
        }
        return ledgerListCustom;
    }

    @Override
    public List<Ledger> getKreditHarian(int idResto, int itemPerPage, int page) {
        List<Ledger> ledgerListKreditHarian = new ArrayList<>();
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT *" + " FROM " + tableLedger + " WHERE " + tipe + "=?::" + tipeLedger +
                            " AND " + this.idResto + "=? LIMIT ? OFFSET ?"
            );
            preparedStatement.setString(1, tipeKredit);
            preparedStatement.setInt(2, idResto);
            preparedStatement.setInt(3, itemPerPage);
            preparedStatement.setInt(4, (page-1)*itemPerPage);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ledger.setWaktu(resultSet.getString("waktu"));
                ledger.setKeperluan(resultSet.getString("keperluan"));
                ledger.setBiaya(resultSet.getInt("biaya"));
                ledgerListKreditHarian.add(ledger);
            }
        } catch (Exception ex){
            System.out.println("Gagal get kredit harian : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return ledgerListKreditHarian;
    }

    @Override
    public int getTotalDebitBulanan(int idResto, int monthh, int yearr) {
        int totalDebitBulanan=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
          preparedStatement = connection.prepareStatement(
                  "SELECT SUM(" + biaya + ") FROM " + tableLedger +
                          " WHERE " + this.idResto + "=? AND " + month + "=? AND " + year + "=? AND " +
                          tipe + "=?::" + tipeLedger
          );
          preparedStatement.setInt(1, idResto);
          preparedStatement.setInt(2, monthh);
          preparedStatement.setInt(3, yearr);
          preparedStatement.setString(4, tipeDebit);
          resultSet = preparedStatement.executeQuery();
          while(resultSet.next()){
              totalDebitBulanan = resultSet.getInt("sum");
          }
        } catch (Exception ex){
            System.out.println("Gagal get total debit bulanan : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return totalDebitBulanan;
    }

    @Override
    public int getTotalDebitTahunan(int idResto, int yearr) {
        int totalDebitTahunan=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT SUM(" + biaya + ") FROM " + tableLedger +
                            " WHERE " + this.idResto + "=? AND " + year + "=? AND " + tipe + "=?::" + tipeLedger
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, yearr);
            preparedStatement.setString(3, tipeDebit);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                totalDebitTahunan = resultSet.getInt("sum");
            }
        } catch (Exception ex){
            System.out.println("Gagal get total debit tahunan : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return totalDebitTahunan;
    }

    @Override
    public int getTotalDebitCustom(int idResto, int tanggal1, int month1, int year1, int tanggal2, int month2, int year2) {
        int totalDebitCustom=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT SUM(" + biaya + ") FROM " + tableLedger +
                            " WHERE " + this.idResto + "=? AND " + tanggal + " BETWEEN ? AND ? AND " + month + " BETWEEN ? AND ? AND " + year + " BETWEEN ? AND ? AND " +
                            tipe + "=?::" + tipeLedger
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, tanggal1);
            preparedStatement.setInt(3, tanggal2);
            preparedStatement.setInt(4, month1);
            preparedStatement.setInt(5, month2);
            preparedStatement.setInt(6, year1);
            preparedStatement.setInt(7, year2);
            preparedStatement.setString(8, tipeDebit);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                totalDebitCustom = resultSet.getInt("sum");
            }
        } catch (Exception ex){
            System.out.println("Gagal get total debit custom : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return totalDebitCustom;
    }

    @Override
    public int getTotalKreditBulanan(int idResto, int monthh, int yearr) {
        int totalKreditBulanan=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT SUM(" + biaya + ") FROM " + tableLedger +
                            " WHERE " + this.idResto + "=? AND " + month + "=? AND " + year + "=? AND " +
                            tipe + "=?::" + tipeLedger
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, monthh);
            preparedStatement.setInt(3, yearr);
            preparedStatement.setString(4, tipeKredit);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                totalKreditBulanan = resultSet.getInt("sum");
            }
        } catch (Exception ex){
            System.out.println("Gagal get total kredit bulanan : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return totalKreditBulanan;
    }

    @Override
    public int getTotalKreditTahunan(int idResto, int yearr) {
        int totalKreditTahunan=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT SUM(" + biaya + ") FROM " + tableLedger +
                            " WHERE " + this.idResto + "=? AND " + year + "=? AND " + tipe + "=?::" + tipeLedger
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, yearr);
            preparedStatement.setString(3, tipeKredit);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                totalKreditTahunan = resultSet.getInt("sum");
            }
        } catch (Exception ex){
            System.out.println("Gagal get total kredit tahunan : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return totalKreditTahunan;
    }

    @Override
    public int getTotalKreditCustom(int idResto, int tanggal1, int month1, int year1, int tanggal2, int month2, int year2) {
        int totalKreditCustom=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT SUM(" + biaya + ") FROM " + tableLedger +
                            " WHERE " + this.idResto + "=? AND " + tanggal + " BETWEEN ? AND ? AND " + month + " BETWEEN ? AND ? AND " + year + " BETWEEN ? AND ? AND " +
                            tipe + "=?::" + tipeLedger
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setInt(2, tanggal1);
            preparedStatement.setInt(3, tanggal2);
            preparedStatement.setInt(4, month1);
            preparedStatement.setInt(5, month2);
            preparedStatement.setInt(6, year1);
            preparedStatement.setInt(7, year2);
            preparedStatement.setString(8, tipeKredit);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                totalKreditCustom = resultSet.getInt("sum");
            }
        } catch (Exception ex){
            System.out.println("Gagal get total kredit custom : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return totalKreditCustom;
    }

    @Override
    public void createTable() throws SQLException {
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + tableLedger +
                            "(" +
                            idLedger + " SERIAL PRIMARY KEY, " +
                            idResto + " INT NOT NULL, " +
                            tipe + " " + tipeLedger + " NOT NULL, " +
                            biaya + " INT NOT NULL, " +
                            keperluan + " TEXT NOT NULL, " +
                            waktu + " TEXT NOT NULL, " +
                            tanggal + " INT NOT NULL, " +
                            week + " INT NOT NULL, " +
                            month + " INT NOT NULL, " +
                            year + " INT NOT NULL, " +
                            "CONSTRAINT id_resto_fk FOREIGN KEY(" + idResto + ")" + " REFERENCES " + refTableResto + "(id))"
            );
            preparedStatement.executeUpdate();
        } catch (Exception ex){

        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public void insert(Ledger modelData, Integer idResto) {
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + tableLedger +
                            "(" +
                            this.idResto + "," +
                            tipe + "," +
                            biaya + "," +
                            keperluan + "," +
                            waktu + "," +
                            tanggal + "," +
                            week + "," +
                            month + "," +
                            year + ")" +
                            "VALUES (?,?::" + tipeLedger + ",?,?,?,?,?,?,?)"
            );
            preparedStatement.setInt(1, idResto);
            preparedStatement.setString(2, modelData.getTipe());
            preparedStatement.setDouble(3, modelData.getBiaya());
            preparedStatement.setString(4, modelData.getKeperluan());
            preparedStatement.setString(5, modelData.getWaktu());
            preparedStatement.setInt(6, modelData.getTanggal());
            preparedStatement.setInt(7, modelData.getWeek());
            preparedStatement.setInt(8, modelData.getMonth());
            preparedStatement.setInt(9, modelData.getYear());
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal insert ledger : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public List<Ledger> readAll(Integer idResto, Integer itemPerPage, Integer page) {
        return null;
    }

    @Override
    public Ledger readOne(Integer idData) {
        return null;
    }

    @Override
    public int getLastId(Integer idResto) {
        int lastId=0;
        Connection connection = DbConnection.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT MAX(" + idLedger + ") FROM " + tableLedger
            );
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                lastId = resultSet.getInt(idLedger);
            }
        } catch (Exception ex){
            System.out.println("Gagal get last id ledger : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return lastId;
    }

    @Override
    public void update(Ledger modelData, Integer idResto) {

    }

    @Override
    public void delete(Integer idData) {

    }

    @Override
    public int count(Integer idResto) {
        return 0;
    }
}
