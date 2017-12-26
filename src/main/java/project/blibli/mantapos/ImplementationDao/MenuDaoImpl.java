package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Model.Menu;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.MenuDao;
import project.blibli.mantapos.Mapper.MenuMapper;

import java.util.ArrayList;
import java.util.List;

public class MenuDaoImpl implements MenuDao {

    private static final String table_name = "menu";
    private static final String id = "id";
    private static final String nama_menu = "nama_menu";
    private static final String harga_menu = "harga_menu";
    private static final String lokasi_gambar_menu = "lokasi_gambar_menu";
    private static final String kategori_menu = "kategori_menu";
    private static final String id_resto = "id_resto";
    private static final String enabled = "enabled";
    private static final String ref_table_resto = "restoran";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public MenuDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(" + id + " SERIAL PRIMARY KEY, " +
                nama_menu + " TEXT NOT NULL, " +
                harga_menu + " REAL NOT NULL, " +
                lokasi_gambar_menu + " TEXT NOT NULL, " +
                kategori_menu + " TEXT, " +
                id_resto + " INT NOT NULL, " +
                enabled + " boolean not null default true, " +
                "CONSTRAINT id_resto_fk FOREIGN KEY (" + id_resto + ") REFERENCES " + ref_table_resto + "(id))";
        try{
            jdbcTemplate.execute(query);
        } catch (Exception ex){
            System.out.println("Gagal create table menu : " + ex.toString());
        }
    }

    @Override
    public void Insert(int id_restoo, Menu menu) {
        String query = "INSERT INTO " + table_name +
                "(" +
                nama_menu + "," +
                harga_menu + "," +
                lokasi_gambar_menu + "," +
                kategori_menu + "," +
                id_resto + ")" +
                "VALUES(?,?,?,?,?)";
        try{
            jdbcTemplate.update(query, new Object[]{
                    menu.getNama_menu(),
                    menu.getHarga_menu(),
                    menu.getLokasi_gambar_menu(),
                    menu.getKategori_menu(),
                    id_restoo
            });
        } catch (Exception ex){
            System.out.println("Gagal insert menu baru : " + ex.toString());
        }
    }

    @Override
    public List<Menu> getAllMenu(int id_restoo, int itemPerPage, int page) {
        List<Menu> menuList = new ArrayList<>();
        String query = null;
        if (itemPerPage==0 && page == 0){
            query = "SELECT * FROM " + table_name + " WHERE " +
                    id_resto + "=? AND " +
                    enabled + "=?" + " " +
                    "ORDER BY " + kategori_menu + " DESC";
            try{
                menuList = jdbcTemplate.query(query, new Object[] {id_restoo, true}, new MenuMapper());
            } catch (Exception ex){
                System.out.println("Gagal get all menu : " + ex.toString());
            }
        } else if(page==0) {
            query = "SELECT * FROM " + table_name + " WHERE " +
                    id_resto + "=? AND " +
                    enabled + "=?" + " " +
                    "ORDER BY " + kategori_menu + " DESC LIMIT ? OFFSET ?";
            try{
                menuList = jdbcTemplate.query(query, new Object[] {id_restoo, true, itemPerPage, (page-1)*itemPerPage}, new MenuMapper());
            } catch (Exception ex){
                System.out.println("Gagal get all menu : " + ex.toString());
            }
        } else {
            query = "SELECT * FROM " + table_name + " WHERE " +
                    id_resto + "=? AND " +
                    enabled + "=?" + " " +
                    "ORDER BY " + kategori_menu + " DESC LIMIT ? OFFSET ?";
            try{
                menuList = jdbcTemplate.query(query, new Object[] {id_restoo, true, itemPerPage, (page-1)*itemPerPage}, new MenuMapper());
            } catch (Exception ex){
                System.out.println("Gagal get all menu : " + ex.toString());
            }
        }
        return menuList;
    }

    @Override
    public int getLastId(int id_restoo) {
        int lastId=0;
        String query = " SELECT MAX(" + id + ") FROM " + table_name + " WHERE " + id_resto + "=?";
        try{
            lastId = jdbcTemplate.queryForObject(query, new Object[] {id_restoo}, Integer.class);
        } catch (Exception ex){
            System.out.println("Gagal get last id : " + ex.toString());
        }
        return lastId+1;
    }

    @Override
    public void DeleteMenu(int idd) {
        String query = "UPDATE " + table_name + " SET " + enabled + "=? WHERE " + id + "=?";
        jdbcTemplate.update(query, new Object[] {false, idd});
    }

    @Override
    public List<Menu> getMenuById(int id_restoo, int id_menu) {
        List<Menu> menuList = new ArrayList<>();
        String query = "SELECT * FROM " + table_name + " WHERE " + id_resto + "=? AND " + id + "=?";
        try{
            menuList = jdbcTemplate.query(query, new Object[] {id_restoo, id_menu}, new MenuMapper());
        } catch (Exception ex){
            System.out.println("Gagal GetMenuById : " + ex.toString());
        }
        return menuList;
    }

    @Override
    public void UpdateMenu(int id_restoo, Menu menu) {
        String query = "UPDATE " + table_name + " SET " + nama_menu + "=?, " + harga_menu + "=?, " + kategori_menu + "=?, " + lokasi_gambar_menu + "=?" +
                " WHERE " + id + "=? AND " + id_resto + "=?";
        jdbcTemplate.update(query, new Object[] {
                menu.getNama_menu(), menu.getHarga_menu(), menu.getKategori_menu(), menu.getLokasi_gambar_menu(), menu.getId(), id_restoo
        });
    }

    @Override
    public int jumlahMenu(int id_restoo) {
        int jumlahMenu=0;
        try{
            String query = "SELECT COUNT(*) FROM " + table_name + " WHERE " + id_resto + "=?";
            jumlahMenu = jdbcTemplate.queryForObject(query, new Object[]{
                    id_restoo
            }, Integer.class);
        } catch (Exception ex){
            System.out.println("Gagal get jumlah menu : " + ex.toString());
        }
        return jumlahMenu;
    }
}
