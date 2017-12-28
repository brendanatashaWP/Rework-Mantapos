package project.blibli.mantapos.NewImplementationDao;

import project.blibli.mantapos.Model.Restoran;
import project.blibli.mantapos.NewInterfaceDao.RestoranDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TODO : restoranDaoImpl pindahkan connection, preparedStatement, dan resultSet jadi didalam masing2 method
public class RestoranDaoImpl implements RestoranDao {

    private static final String tableRestoran = "restoran";
    private static final String idResto = "id";
    private static final String namaResto = "nama_restoran";
    private static final String alamatResto = "alamat_resto";

    private static final String idUser = "id";
    private static final String usernameUser = "username";
    private static final String passwordUser = "password";
    private static final String namaLengkap = "nama_lengkap";
    private static final String jenisKelamin = "jenis_kelamin";
    private static final String nomorKtp = "nomor_ktp";
    private static final String nomorTelepon = "nomor_telepon";
    private static final String alamatUser = "alamat";

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Restoran restoran = new Restoran();
    int idRestoran=0, count=0;

    @Override
    public void createTable(){
        connection = DbConnection.openConnection();
        try {
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + tableRestoran + "(" +
                            idResto + " SERIAL PRIMARY KEY, " +
                            namaResto + " TEXT NOT NULL, " +
                            alamatResto + " TEXT NOT NULL, " +
                            "UNIQUE (" + namaResto + "))" //TODO : Kenapa nama resto gakmau ter-constraint unique (nama resto sama masih bisa)
                    //kenapa nama resto dibuat unique???
                    //supaya waktu get ID resto berdasarkan nama restoran, return nama resto nya cuma 1 aja
                    //jadi gimana kalau ada 2 restoran yang sama yg menggunakan mantapos?
                    //misal Afui, ya dibuat Afui 1 dan Afui 2. Atau Afui babarsari dan Afui jakal
                    //pokoknya namanya dibuat unique
            );
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal create table resto : " + e.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public void insert(Restoran modelData, Integer idResto) {
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + tableRestoran + "(" + namaResto + "," + alamatResto + ")" +
                            "VALUES(?,?)"
            );
            preparedStatement.setString(1, modelData.getNamaResto());
            preparedStatement.setString(2, modelData.getLokasiResto());
            preparedStatement.executeUpdate();
            System.out.println("Berhasil insert restoran " + modelData.getNamaResto());
        } catch (Exception ex){
            System.out.println("Gagal insert restoran " + modelData.getNamaResto() + " : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    //Di-replace dengan method readAllRestoran karena butuh parameter role
    @Override
    public List<Restoran> readAll(Integer idResto, Integer itemPerPage, Integer page) {
        return null;
    }

    @Override
    public Restoran readOne(Integer idData) {
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM " + tableRestoran + " WHERE " + idResto + "=?"
            );
            preparedStatement.setInt(1, idData);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                restoran.setId(resultSet.getInt(idData));
                restoran.setNamaResto(resultSet.getString(namaResto));
                restoran.setAlamat(resultSet.getString(alamatResto));
            }
        } catch (Exception ex){
            System.out.println("Gagal get restoran dengan id " + idData + " : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return restoran;
    }

    @Override
    public int getLastId(Integer idResto) {
        return 0;
    }

    // TODO : perlukah update informasi restoran?
    @Override
    public void update(Restoran modelData, Integer idResto) {

    }

    //Tidak perlu delete restoran, karena yg di-delete (dinonaktifkan) hanya cukup owner-nya saja
    @Override
    public void delete(Integer idData) {

    }

    //Di-replace dengan method countRestoran, karena gak butuh parameter idResto
    @Override
    public int count(Integer idResto) {
        return 0;
    }

    //Mengambil id restoran berdasarkan nama restoran
    @Override
    public int readIdResto(String namaResto) {
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT " + idResto + " FROM " + tableRestoran + " WHERE " + this.namaResto + "=?"
            );
            preparedStatement.setString(1, namaResto);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                idRestoran = resultSet.getInt(idResto);
            }
        } catch (Exception ex){
            System.out.println("Gagal read id resto berdasarkan nama restoran " + namaResto + " : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return idRestoran;
    }

    @Override
    public List<Restoran> readAllRestoran(String role) {
        List<Restoran> restoranList = new ArrayList<>();
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT" +
                            " *, " + //semua field restoran
                            " *" + //semua field user dari table users
                            " FROM restoran, users, user_roles" +
                            " WHERE users.id_resto=restoran.id " +
                            "AND user_roles.id=users.id " +
                            "AND user_roles.role=?::role_type"
            );
            preparedStatement.setString(1, role);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                restoran.setId(resultSet.getInt(idResto));
                restoran.setNamaResto(resultSet.getString(namaResto));
                restoran.setLokasiResto(resultSet.getString(alamatResto));
                restoran.setIdUser(resultSet.getInt(idUser));
                restoran.setNamaLengkap(resultSet.getString(namaLengkap));
                restoran.setJenisKelamin(resultSet.getString(jenisKelamin));
                restoran.setUsername(resultSet.getString(usernameUser));
                restoran.setPassword(resultSet.getString(passwordUser));
                restoran.setNomorKtp(resultSet.getString(nomorKtp));
                restoran.setNomorTelepon(resultSet.getString(nomorTelepon));
                restoran.setAlamat(resultSet.getString(alamatUser));
                restoranList.add(restoran);
            }
        } catch (Exception ex){
            System.out.println("Gagal read All data from " + tableRestoran + " : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return restoranList;
    }

    @Override
    public int countRestoran() {
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM " + tableRestoran
            );
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                count = resultSet.getInt("sum");
            }
        } catch (Exception ex){
            System.out.println("Gagal count restoran : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return count;
    }
}
