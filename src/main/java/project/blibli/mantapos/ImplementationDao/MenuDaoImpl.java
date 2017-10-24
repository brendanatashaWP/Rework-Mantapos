package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Beans_Model.Menu;
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
    private static final String ref_table_resto = "restoran";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public MenuDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(" + id + " SERIAL PRIMARY KEY, " +
                nama_menu + " TEXT, " +
                harga_menu + " TEXT, " +
                lokasi_gambar_menu + " TEXT, " +
                kategori_menu + " TEXT, " +
                id_resto + " INT NOT NULL, " +
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
    public List<Menu> getAllMenu(int id_restoo) {
        List<Menu> menuList = new ArrayList<>();
        String query = "SELECT * FROM " + table_name + " WHERE " + id_resto + "=? ORDER BY " + kategori_menu + " DESC";
        try{
            menuList = jdbcTemplate.query(query, new Object[] {id_restoo}, new MenuMapper());
        } catch (Exception ex){
            System.out.println("Gagal get all menu : " + ex.toString());
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
}
