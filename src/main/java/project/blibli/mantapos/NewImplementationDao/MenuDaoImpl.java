package project.blibli.mantapos.NewImplementationDao;

import project.blibli.mantapos.Model.Menu;
import project.blibli.mantapos.NewInterfaceDao.MenuDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TODO : buat owner/manager bisa mendaftarkan kategori sendiri (inilah kenapa kategori gak dibuat type sendiri seperti user roles)
//TODO : getLastId diganti menjadi search by idOrder yg unique yang di-pass dari HTML atau javascript
public class MenuDaoImpl implements MenuDao {

    private static final String tableMenu = "menu";
    private static final String idMenu = "id";
    private static final String namaMenu = "nama_menu";
    private static final String hargaMenu = "harga_menu";
    private static final String lokasiGambarMenu = "lokasi_gambar_menu";
    private static final String kategoriMenu = "kategori_menu";
    private static final String enabled = "enabled";
    private static final String refTableRestoran = "restoran";
    private static final String idResto = "id_resto";

    @Override
    public void createTable() throws SQLException {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + tableMenu +
                            "(" + idMenu + " SERIAL PRIMARY KEY, " +
                            namaMenu + " TEXT NOT NULL, " +
                            hargaMenu + " REAL NOT NULL, " +
                            lokasiGambarMenu + " TEXT NOT NULL, " +
                            kategoriMenu + " TEXT, " +
                            idResto + " INT NOT NULL, " +
                            enabled + " boolean not null default true, " +
                            "CONSTRAINT id_resto_fk FOREIGN KEY (" + idResto + ") REFERENCES " + refTableRestoran + "(id))"
            );
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal create table " + tableMenu + " : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public void insert(Menu modelData, Integer idResto) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + tableMenu +
                            "(" +
                            namaMenu + "," +
                            hargaMenu + "," +
                            lokasiGambarMenu + "," +
                            kategoriMenu + "," +
                            this.idResto + ")" +
                            "VALUES(?,?,?,?,?)"
            );
            preparedStatement.setString(1, modelData.getNama_menu());
            preparedStatement.setDouble(2, modelData.getHarga_menu());
            preparedStatement.setString(3, modelData.getLokasi_gambar_menu());
            preparedStatement.setString(4, modelData.getKategori_menu());
            preparedStatement.setInt(5, idResto);
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal insert menu " + namaMenu + " : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public List<Menu> readAll(Integer idResto, Integer itemPerPage, Integer page) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Menu> menuList = new ArrayList<>();
        connection = DbConnection.openConnection();
        try{
            if(itemPerPage==0 && page==0){ //artinya yang akses untuk mengambil semua daftar menu adalah dari laman cashier (gak perlu pagination)
                preparedStatement = connection.prepareStatement(
                        "SELECT * FROM " + tableMenu + " WHERE " +
                                this.idResto + "=? AND " +
                                enabled + "=?" + " " +
                                "ORDER BY " + kategoriMenu + " DESC"
                );
                preparedStatement.setInt(1, idResto);
                preparedStatement.setBoolean(2, true);
            } else { //itemPerPage tidak nol dan page tidak nol, artinya yang mengakses ini butuh pagination
                preparedStatement = connection.prepareStatement(
                        "SELECT * FROM " + tableMenu + " WHERE " +
                                this.idResto + "=? AND " +
                                enabled + "=?" + " " +
                                "ORDER BY " + kategoriMenu + " DESC LIMIT ? OFFSET ?"
                );
                preparedStatement.setInt(1, idResto);
                preparedStatement.setBoolean(2, true);
                preparedStatement.setInt(3, itemPerPage);
                preparedStatement.setInt(4, (page-1)*itemPerPage);
            }
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Menu menu = new Menu();
                menu.setId(resultSet.getInt(idMenu));
                menu.setNama_menu(resultSet.getString(namaMenu));
                menu.setHarga_menu(resultSet.getDouble(hargaMenu));
                menu.setKategori_menu(resultSet.getString(kategoriMenu));
                menu.setLokasi_gambar_menu(resultSet.getString(lokasiGambarMenu));
                menuList.add(menu);
            }
        } catch (Exception ex){
            System.out.println("Gagal readAllMenu : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return menuList;
    }

    @Override
    public Menu readOne(Integer idData) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connection = DbConnection.openConnection();
        Menu menu = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM " + tableMenu + " WHERE " + idMenu + "=?"
            );
            preparedStatement.setInt(1, idData);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                menu = new Menu();
                menu.setId(resultSet.getInt(idMenu));
                menu.setNama_menu(resultSet.getString(namaMenu));
                menu.setHarga_menu(resultSet.getDouble(hargaMenu));
                menu.setKategori_menu(resultSet.getString(kategoriMenu));
                menu.setLokasi_gambar_menu(resultSet.getString(lokasiGambarMenu));
            }
        } catch (Exception ex){
            System.out.println("Gagal read one menu " + idData + " : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return menu;
    }

    //TODO : Masih salah
    @Override
    public int getLastId(Integer idResto) {
        int lastId=0;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT MAX(" + idMenu + ") FROM " + tableMenu + " WHERE " + this.idResto + "=?"
            );
            preparedStatement.setInt(1, idResto);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                lastId = resultSet.getInt(idMenu);
            }
        } catch (Exception ex){
            System.out.println("Gagal get last id menu : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return lastId;
    }

    @Override
    public void update(Menu modelData, Integer idResto) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "UPDATE " + tableMenu + " SET " + namaMenu + "=?, " + hargaMenu + "=?, " + kategoriMenu + "=?, " + lokasiGambarMenu + "=?" +
                            " WHERE " + idMenu + "=?"
            );
            preparedStatement.setString(1, modelData.getNama_menu());
            preparedStatement.setDouble(2, modelData.getHarga_menu());
            preparedStatement.setString(3, modelData.getKategori_menu());
            preparedStatement.setString(4, modelData.getLokasi_gambar_menu());
            preparedStatement.setInt(5, modelData.getId());
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal update menu " + modelData.getId() + " : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public void delete(Integer idData) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "UPDATE " + tableMenu + " SET " + enabled + "=? WHERE " + idMenu + "=?"
            );
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, idData);
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal delete menu " + idData + " : " + ex.toString());
        } finally {
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
    }

    @Override
    public int count(Integer idResto) {
        int count=0;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connection = DbConnection.openConnection();
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM " + tableMenu + " WHERE " + this.idResto + "=?"
            );
            preparedStatement.setInt(1, idResto);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                count = resultSet.getInt(1);
            }
        } catch (Exception ex){
            System.out.println("Gagal get count menu restoran " + idResto + " : " + ex.toString());
        } finally {
            DbConnection.closeResultSet(resultSet);
            DbConnection.closePreparedStatement(preparedStatement);
            DbConnection.closeConnection(connection);
        }
        return count;
    }
}
