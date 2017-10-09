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
    private static final String name = "name";
    private static final String price = "price";
    private static final String photo_link = "photo_link";
    private static final String category = "category";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public MenuDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(" + id + " SERIAL PRIMARY KEY, " +
                name + " TEXT, " +
                price + " TEXT, " +
                photo_link + " TEXT, " +
                category + " TEXT)";
        jdbcTemplate.execute(query);
    }

    @Override
    public int Insert(Menu menu) {
        int status;
        String query = "INSERT INTO " + table_name +
                "(" +
                name + "," +
                price + "," +
                photo_link + "," +
                category + ")" +
                "VALUES(?,?,?,?)";
        status = jdbcTemplate.update(query, new Object[]{
                menu.getName_menu(),
                menu.getPrice_total_menu(),
                menu.getPhoto_location_menu(),
                menu.getCategory_menu()
        });
        return status;
    }

    @Override
    public List<Menu> getAllMenu() {
        List<Menu> menuList = new ArrayList<>();
        String query = "SELECT * FROM " + table_name;
        menuList = jdbcTemplate.query(query, new MenuMapper());
        return menuList;
    }

    //TODO : Benerin queryforobject supaya return nama menu, yang ini errornya Incorrect result size: expected 1, actual 3
    @Override
    public String getMenuById(int id) {
        System.out.println("ID : " + id);
        String name_menu = null;
        String query = "SELECT " + name + " FROM " + table_name + " WHERE " + id + "=?";
        name_menu = jdbcTemplate.queryForObject(query, new Object[] {id}, String.class);
        return name_menu;
    }
}
