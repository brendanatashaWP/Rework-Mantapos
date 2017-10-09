package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.OrderedMenuDao;

public class OrderedMenuDaoImpl implements OrderedMenuDao {
    private static final String table_name = "ordered_menu";
    private static final String id_order = "id_order";
    private static final String id_menu = "id_menu";
    private static final String menu_name = "menu";
    private static final String qty = "quantity";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private MenuDaoImpl menuDao = new MenuDaoImpl();

    public OrderedMenuDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(" +
                id_order + " INT NOT NULL, " +
                id_menu + " INT NOT NULL, " +
                menu_name + " TEXT NOT NULL, " +
                qty + " INT NOT NULL)";
        jdbcTemplate.execute(query);
    }

    @Override
    public int Insert(int lastId_IdOrder, int id_order_menu, int qty_insert) {
        int status;
        String query = "INSERT INTO " + table_name +
                "(" +
                id_order + "," +
                id_menu + "," +
                menu_name + "," +
                qty + ")" +
                " VALUES (?,?,?,?)";
        status = jdbcTemplate.update(query, new Object[]{
                lastId_IdOrder, id_order_menu, menuDao.getMenuById(id_order_menu), qty_insert
        });
        return status;
    }
}
